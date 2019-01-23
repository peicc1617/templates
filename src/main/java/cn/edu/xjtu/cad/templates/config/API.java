package cn.edu.xjtu.cad.templates.config;

public class API {
    String host;
    String port;
    String context;
    String version;
    String api;
    public API() {
    }

    public API(String host, String port, String context, String version) {
        this.host = host;
        this.port = port;
        this.context = context;
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
