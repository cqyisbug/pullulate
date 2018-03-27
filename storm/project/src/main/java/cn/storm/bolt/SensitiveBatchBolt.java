package cn.storm.bolt;

import cn.storm.bean.RubbishUsers;
import org.apache.storm.shade.org.apache.commons.collections.Predicate;
import org.apache.storm.shade.org.apache.commons.collections.iterators.FilterIterator;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SensitiveBatchBolt implements IBasicBolt {
    private static List list = new ArrayList<>(Arrays.asList(RubbishUsers.SENSITIVE_KEYWORDS));

    private class SensitivePredicate implements Predicate {
        private String sensitiveWord = null;

        SensitivePredicate(String sensitiveWord) {
            this.sensitiveWord = sensitiveWord;
        }

        @Override
        public boolean evaluate(Object object) {
            return this.sensitiveWord.contains((String) object);
        }
    }

    class SensitiveMonitorThread implements Runnable {
        private int sensitiveMonitorTimeInterval = 0;

        public SensitiveMonitorThread(int sensitiveMonitorTimeInterval) {
            this.sensitiveMonitorTimeInterval = sensitiveMonitorTimeInterval;
        }

        @Override
        public void run() {
            while (true) {

            }
        }
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
//        final int sensitiveMonitorTimeInterval = Integer.parseInt(stormConf.get("RUBBISHMONITOR_INTERVAL").toString())
//        SensitiveMonitorThread thread = new SensitiveMonitorThread(sensitiveMonitorTimeInterval);
//        new Thread(thread).start();

    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        save(input);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    private synchronized void save(Tuple input) {
        RubbishUsers users = new RubbishUsers();
        users.setUserId(Integer.parseInt(input
                .getStringByField(RubbishUsers.USERID_COLUMNNAME)));
        users.setHomeCity(Integer.parseInt(input
                .getStringByField(RubbishUsers.HOMECITY_COLUMNNAME)));
        users.setMsisdn(Integer.parseInt(input
                .getStringByField(RubbishUsers.MSISDN_COLUMNNAME)));
        users.setSmsContent(input
                .getStringByField(RubbishUsers.SMSCONTENT_COLUMNNAME));

        Predicate isSensitiveFileAnalysis = new SensitivePredicate(
                (String) input.getStringByField(RubbishUsers.SMSCONTENT_COLUMNNAME));

        FilterIterator iterator = new FilterIterator(list.iterator(), isSensitiveFileAnalysis);

        if (iterator.hasNext()) {
            System.out.println(users);
        }
    }
}
