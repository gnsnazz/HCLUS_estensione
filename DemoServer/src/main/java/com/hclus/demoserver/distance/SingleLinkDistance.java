package com.hclus.demoserver.distance;

import com.hclus.demoserver.clustering.Cluster;
import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.data.Example;
import com.hclus.demoserver.data.InvalidSizeException;

/**
 * Calcola la distanza tra due cluster.
 */
public class SingleLinkDistance implements ClusterDistance {
	/**
	 * Restituisce la minima distanza tra due cluster con la distanza SingleLink.
	 *
	 * @param c1  primo cluster
	 * @param c2  secondo cluster
	 * @param d  dataset
	 *
	 * @return minima distanza tra i cluster
	 */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
		double min = Double.MAX_VALUE;

        for (Integer integer : c1) {
            Example ex1 = d.getExample(integer);
            for (Integer value : c2) {
                double distance = ex1.distance(d.getExample(value));
                if (distance < min)
                    min = distance;
            }
        }
		return min;
	}

}