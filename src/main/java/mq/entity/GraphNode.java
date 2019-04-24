package mq.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fansy
 * @Time: 2019/4/16 11:11
 * @Email: fansy1990@foxmail.com
 */
public class GraphNode {

    private String className;
    private NodeStatus status = NodeStatus.INIT;// initial  status
    private Map<String, String> args = new HashMap<>();
    private String id;

    public GraphNode() {
    }

    public GraphNode(NodeStatus status, Map<String, String> args, String id,String clasName) {
        this.status = status;
        this.args = args;
        this.id = id;
        this.className = clasName;
    }
    public GraphNode(NodeStatus status, Map<String, String> args, String id) {
        this.status = status;
        this.args = args;
        this.id = id;
    }

    public GraphNode(Map<String, String> args, String id) {
        this.args = args;
        this.id = id;
    }

    public GraphNode(String id) {
        this.args.put("id", id);// 默认参数
        this.id = id;
    }
    public GraphNode(String id,String className) {
        this.args.put("id", id);// 默认参数
        this.id = id;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
