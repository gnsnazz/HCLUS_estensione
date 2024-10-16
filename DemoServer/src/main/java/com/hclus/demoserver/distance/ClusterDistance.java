package com.hclus.demoserver.distance;

import com.hclus.demoserver.clustering.Cluster;
import com.hclus.demoserver.data.Data;
import com.hclus.demoserver.data.InvalidSizeException;

/**
 * Interfaccia per il calcolo della distanza tra due cluster.
 *
 * @author Nazz
 */
public interface ClusterDistance {
	/**
	 * Distanza tra due cluster.
	 *
	 * @param c1  primo cluster
	 * @param c2  secondo cluster
	 * @param d  dataset
	 *
	 * @return la distanza tra cluster
	 *
	 * @throws InvalidSizeException se la dimensione del cluster è minore di 2
	 */
	double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException;

}