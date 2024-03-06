package org.co.ds.kafka.connect.jdbc;

import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.co.ds.kafka.connect.jdbc.util.CachedConnectionProvider;

import java.util.List;
import java.util.Map;

public class BatchJdbcSourceConnector extends JdbcSourceConnector{

    @Override
    public String version() {
        return super.version();
    }

    @Override
    public void start(Map<String, String> properties) throws ConnectException {
        super.start(properties);
    }

    @Override
    protected CachedConnectionProvider connectionProvider(int maxConnAttempts, long retryBackoff) {
        return super.connectionProvider(maxConnAttempts, retryBackoff);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return BatchJdbcSourceTask.class;
    }

    @Override
    public Config validate(Map<String, String> connectorConfigs) {
        return super.validate(connectorConfigs);
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        return super.taskConfigs(maxTasks);
    }

    @Override
    public void stop() throws ConnectException {
        super.stop();
    }

    @Override
    public ConfigDef config() {
        return super.config();
    }
}
