package org.co.ds.kafka.connect.jdbc;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.source.SourceRecord;
import org.co.ds.kafka.connect.jdbc.sink.JdbcSinkTask;
import org.co.ds.kafka.connect.jdbc.source.JdbcSourceTask;
import org.co.ds.kafka.connect.jdbc.util.CachedConnectionProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class BatchJdbcSourceTask extends JdbcSourceTask {
    public BatchJdbcSourceTask() {
        super();
    }

    public BatchJdbcSourceTask(Time time) {
        super(time);
    }

    @Override
    public String version() {
        return super.version();
    }

    @Override
    public void start(Map<String, String> properties) {
        super.start(properties);
    }

    @Override
    protected CachedConnectionProvider connectionProvider(int maxConnAttempts, long retryBackoff) {
        return super.connectionProvider(maxConnAttempts, retryBackoff);
    }

    @Override
    protected Map<String, Object> computeInitialOffset(String tableOrQuery, Map<String, Object> partitionOffset, TimeZone timezone) {
        return super.computeInitialOffset(tableOrQuery, partitionOffset, timezone);
    }

    @Override
    public void stop() throws ConnectException {
        super.stop();
    }

    @Override
    protected void closeResources() {
        super.closeResources();
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        return super.poll();
    }
}
