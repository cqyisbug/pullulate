package spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class SpoutImpl implements IRichSpout {

    private SpoutOutputCollector collector = null;
    private BufferedReader reader = null;
    private boolean complete = false;

    @Override
    public void nextTuple() {
        if (complete) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        String str;
        try {
            int i = 0;
            while ((str = reader.readLine()) != null) {
                System.out.println("WordReader.nextTuple(),emits time:" + i++);
                collector.emit(new Values(str), str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //字段定义
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        String fileName = conf.get("fileName").toString();
        InputStream stream = SpoutImpl.class.getClassLoader().getResourceAsStream(fileName);
        reader = new BufferedReader(new InputStreamReader(stream));
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void ack(Object msgId) {
        System.out.println("acked");
    }

    @Override
    public void fail(Object msgId) {

    }
}
