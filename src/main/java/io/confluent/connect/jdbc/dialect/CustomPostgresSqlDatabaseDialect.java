package io.confluent.connect.jdbc.dialect;

import io.confluent.connect.jdbc.dialect.DatabaseDialectProvider.SubprotocolBasedProvider;
import io.confluent.connect.jdbc.sink.JdbcSinkConfig;
import io.confluent.connect.jdbc.util.*;
import org.apache.kafka.common.config.AbstractConfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomPostgresSqlDatabaseDialect extends PostgreSqlDatabaseDialect{

    /** Name of the timestamp field in the table for travel to the past check **/
     protected final String timestampField;


    public static class Provider extends SubprotocolBasedProvider {
        public Provider() {
            super(CustomPostgresSqlDatabaseDialect.class.getSimpleName(), "postgresql");
        }


        @Override
        public DatabaseDialect create(AbstractConfig config) {
            System.out.println("ABSTRACT CONFIGxxxxxxx" + config.getString(JdbcSinkConfig.LAST_MODIFIED_TS_FIELD));
            System.out.println("ABSTRACT CONFIGxxxxxxx" + config.getString(JdbcSinkConfig.CONNECTION_URL));
            return new CustomPostgresSqlDatabaseDialect(config);
        }
    }

    //private final JdbcSinkConfig config;
    /**
     * Create a new dialect instance with the given connector configuration.
     *
     * @param config the connector configuration; may not be null
     */
    public CustomPostgresSqlDatabaseDialect(AbstractConfig config) {

        this(config,config.getString(JdbcSinkConfig.LAST_MODIFIED_TS_FIELD));
        //this(config,"tc");
    }
    protected CustomPostgresSqlDatabaseDialect(AbstractConfig config, String timestampField) {
        super(config);
        this.timestampField = timestampField;
    }

    @Override
    public String buildUpsertQueryStatement(TableId table, Collection<ColumnId> keyColumns, Collection<ColumnId> nonKeyColumns, TableDefinition definition) {

        final ExpressionBuilder.Transform<ColumnId> transform = (builder, col) -> {
            builder.appendColumnName(col.name())
                    .append("=EXCLUDED.")
                    .appendColumnName(col.name());
        };

        ExpressionBuilder builder = expressionBuilder();
        builder.append("INSERT INTO ");
        builder.append(table);
        builder.append(" (");
        builder.appendList()
                .delimitedBy(",")
                .transformedBy(ExpressionBuilder.columnNames())
                .of(keyColumns, nonKeyColumns);
        builder.append(") VALUES (");
        builder.appendList()
                .delimitedBy(",")
                .transformedBy(this.columnValueVariables(definition))
                .of(keyColumns, nonKeyColumns);
        builder.append(") ON CONFLICT (");
        builder.appendList()
                .delimitedBy(",")
                .transformedBy(ExpressionBuilder.columnNames())
                .of(keyColumns);
        if (nonKeyColumns.isEmpty()) {
            builder.append(") DO NOTHING");
        } else {
            builder.append(") DO UPDATE SET ");
            builder.appendList()
                    .delimitedBy(",")
                    .transformedBy(transform)
                    .of(nonKeyColumns);

            // WHERE "persons_target"."tc" <= EXCLUDED."tc"
            //final String timestampField = "tc";
            final ExpressionBuilder.Transform<ColumnId> transform1 = (builder1, col1) -> {
                builder.appendColumnName(col1.name())
                        .append("<=EXCLUDED.")
                        .appendColumnName(col1.name());
            };
            List<String> colList = Arrays.asList(this.timestampField.split(","));
            builder.append(" WHERE ");
            builder.append(table);
            builder.append(".");

            builder.appendList()
                    .delimitedBy(" AND ")
                    .transformedBy(transform1)
                    .of(asColumns(colList,table));
        }


        return builder.toString();




    }

    private Collection<ColumnId> asColumns(Collection<String> names, TableId tableId) {
        return names.stream()
                .map(name -> new ColumnId(tableId, name))
                .collect(Collectors.toList());
    }
}
