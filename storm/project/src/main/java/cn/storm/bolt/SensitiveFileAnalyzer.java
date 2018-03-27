package cn.storm.bolt;

import cn.storm.bean.RubbishUsers;
import org.apache.storm.shade.com.google.common.base.Splitter;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SensitiveFileAnalyzer extends BaseBasicBolt{
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String line = input.getString(0);
        Map<String,String> join = Splitter.on("&").withKeyValueSeparator("=").split(line);
        collector.emit(new Values(join.get(RubbishUsers.HOMECITY_COLUMNNAME),join.get(RubbishUsers.USERID_COLUMNNAME),
        join.get(RubbishUsers.MSISDN_COLUMNNAME),join.get(RubbishUsers.SMSCONTENT_COLUMNNAME)
        ));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(RubbishUsers.HOMECITY_COLUMNNAME, RubbishUsers.USERID_COLUMNNAME, RubbishUsers.MSISDN_COLUMNNAME, RubbishUsers.SMSCONTENT_COLUMNNAME));
    }
}
