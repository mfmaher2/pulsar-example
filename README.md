# Customer360PulsarExample

Before running your project
Ensure you have added your environemnt vaiables to your run/debug configurations. These credentials can be found on your Astra console

Environemt Variables (delete {}):
BUNDLE_PATH={bundle path};PASSWORD={Credentials password};PULSAR_TOKEN={Pulsar token};USERNAME={username};STORE_TOPIC=persistent://{stream name}/{namespace}/{topic name};

Create a database e.g:
CREATE TABLE stores.transactions (
    email text,
    dateofpurchase timestamp,
    first_name text,
    last_name text,
    location text,
    product text,
    spend int,
    PRIMARY KEY (email, dateofpurchase)
) WITH CLUSTERING ORDER BY (dateofpurchase ASC)
    AND additional_write_policy = '99PERCENTILE'
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.UnifiedCompactionStrategy'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair = 'BLOCKING'
    AND speculative_retry = '99PERCENTILE';
    
    Create a topic under streaming tab. In this example its called purchases. Full URL: persistent://pulsar-example/default/purchases
    Create a sink, connect it to your Astra DB and ensure the collumns are mapped correctly with the topic input.
