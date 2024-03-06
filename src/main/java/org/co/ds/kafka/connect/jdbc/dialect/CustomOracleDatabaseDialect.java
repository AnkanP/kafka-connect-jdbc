package org.co.ds.kafka.connect.jdbc.dialect;

import org.co.ds.kafka.connect.jdbc.util.ColumnId;
import org.co.ds.kafka.connect.jdbc.util.ExpressionBuilder;
import org.co.ds.kafka.connect.jdbc.util.TableId;
import org.apache.kafka.common.config.AbstractConfig;

import java.util.Collection;

public class CustomOracleDatabaseDialect extends OracleDatabaseDialect{


    public static class Provider extends DatabaseDialectProvider.SubprotocolBasedProvider {
        public Provider() {
            super(CustomOracleDatabaseDialect.class.getSimpleName(), "oracle");
        }

        @Override
        public DatabaseDialect create(AbstractConfig config) {
            return new CustomOracleDatabaseDialect(config);
        }
    }

    /**
     * Create a new dialect instance with the given connector configuration.
     *
     * @param config the connector configuration; may not be null
     */
    public CustomOracleDatabaseDialect(AbstractConfig config) {
        super(config);
    }

    @Override
    public String buildUpsertQueryStatement(
            final TableId table,
            Collection<ColumnId> keyColumns,
            Collection<ColumnId> nonKeyColumns
    ) {
        // https://blogs.oracle.com/cmar/entry/using_merge_to_do_an
        final ExpressionBuilder.Transform<ColumnId> transform = (builder, col) -> {
            builder.append(table)
                    .append(".")
                    .appendColumnName(col.name())
                    .append("=incoming.")
                    .appendColumnName(col.name());
        };

        ExpressionBuilder builder = expressionBuilder();
        builder.append("merge into ");
        builder.append(table);
        builder.append(" using (select ");
        builder.appendList()
                .delimitedBy(", ")
                .transformedBy(ExpressionBuilder.columnNamesWithPrefix("? "))
                .of(keyColumns, nonKeyColumns);
        builder.append(" FROM dual) incoming on(");
        builder.appendList()
                .delimitedBy(" and ")
                .transformedBy(transform)
                .of(keyColumns);
        builder.append(")");
        if (nonKeyColumns != null && !nonKeyColumns.isEmpty()) {
            builder.append(" when matched then update set ");
            builder.appendList()
                    .delimitedBy(",")
                    .transformedBy(transform)
                    .of(nonKeyColumns);
        }

        builder.append(" when not matched then insert(");
        builder.appendList()
                .delimitedBy(",")
                .of(nonKeyColumns, keyColumns);
        builder.append(") values(");
        builder.appendList()
                .delimitedBy(",")
                .transformedBy(ExpressionBuilder.columnNamesWithPrefix("incoming."))
                .of(nonKeyColumns, keyColumns);
        builder.append(")");
        return builder.toString();
    }
}
