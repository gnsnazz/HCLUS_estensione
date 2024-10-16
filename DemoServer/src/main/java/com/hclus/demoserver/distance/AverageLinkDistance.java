package com.hclus.demoserver.distance;

import com.hclus.demoserver.clustering.Cluster;
import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.data.Example;
import com.hclus.demoserver.data.InvalidSizeException;

/**
 * Calcola la media della distanza tra due cluster.
 *
 * @author Nazz
 */
public class AverageLinkDistance implements ClusterDistance {
    /**
     * Restituisce la media delle distanze minime tra i cluster con la distanza AverageLink.
     *
     * @param c1  primo cluster
     * @param c2  secondo cluster
     * @param d  dataset
     *
     * @return media selle distanze tra i cluster
     */
    public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
        double sum = 0.0;

        for (Integer integer : c1) {
            Example ex1 = d.getExample(integer);
            for (Integer value : c2) sum += ex1.distance(d.getExample(value));
        }

        return sum / (c1.getSize() * c2.getSize());
    }

}