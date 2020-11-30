package com.onlinebookstore.entity.orderserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 10:24
 */
public class Graph implements Serializable {
    private static final long serialVersionUID = 1764833861333756486L;
    private List<String> xData;
    private List<SeriesItem> series;

    private Graph() { }

    public static class GraphBuilder {
        private final Graph graph;

        public GraphBuilder() {
            graph = new Graph();
        }

        public GraphBuilder setXData(List<String> xData) {
            graph.xData = xData;
            return this;
        }

        public GraphBuilder setSeries(List<SeriesItem> series) {
            graph.series = series;
            return this;
        }

        public Graph build() {
            return graph;
        }
    }

    public List<String> getxData() {
        return xData;
    }

    public List<SeriesItem> getSeries() {
        return series;
    }
}
