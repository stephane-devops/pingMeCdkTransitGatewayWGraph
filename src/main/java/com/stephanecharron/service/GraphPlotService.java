package com.stephanecharron.service;

import com.stephanecharron.model.NetworkTopology;
import com.stephanecharron.model.VpcVertex;
import lombok.Builder;
import lombok.NonNull;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class GraphPlotService {
    static final String GRAPH_PLOT = "graph.plot";

    @NonNull
    private final NetworkTopology networkTopology;
    private Graph<VpcVertex, DefaultEdge> graph;


    @Builder
    public GraphPlotService(@NonNull NetworkTopology networkTopology) {
        this.networkTopology = networkTopology;
    }

    public void execute() {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        addVertex();
        addEdges();
        plot();
    }

    private void addVertex() {
        for (VpcVertex vpcVertex : networkTopology.getVpcMap().values()) {
            graph.addVertex(vpcVertex);
        }
    }

    private void addEdges() {
        for (VpcVertex vpcVertexSource : networkTopology.getVpcMap().values()) {

            for (String link : vpcVertexSource.getVpcTopology().getLinks()) {
                graph.addEdge(vpcVertexSource, networkTopology.getVpcMap().get(link));
            }
        }
    }

    private void plot() {

        DOTExporter<VpcVertex, DefaultEdge> exporter =
                new DOTExporter<>(vpcVertex -> vpcVertex.getVpcTopology().getName());

        exporter.setVertexAttributeProvider((vpcVertex) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(
                    getVertexLabel(vpcVertex)
            ));
            return map;
        });

        try {
            Writer fileWriter = new FileWriter(GRAPH_PLOT);
            exporter.exportGraph(graph, fileWriter);
            fileWriter.write(fileWriter.toString());

            Writer writer = new StringWriter();
            exporter.exportGraph(graph, writer);
            String content = writer.toString();

            Path inPath = Paths.get("index-template.html");
            Path outPath = Paths.get("index.html");

            Charset charset = StandardCharsets.UTF_8;

            String templateContent = new String(Files.readAllBytes(inPath), charset);
            content = templateContent.replaceAll("WILLBERPLACED", content);
            Files.write(outPath, content.getBytes(charset));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String getVertexLabel(VpcVertex vpcVertex) {

        String label = vpcVertex.getVpcTopology().getName() + "\n";
        label += vpcVertex.getVpcTopology().getCidr() + "\n";

        label += vpcVertex.getBastion().getInstanceId().contains("Token") ?
                "" : vpcVertex.getBastion().getInstancePrivateIp() + "\n";

        label += vpcVertex.getBastion().getInstancePrivateIp().contains("Token") ?
                "" : vpcVertex.getBastion().getInstancePrivateIp() + "\n";
        return label;

    }
}
