package saveOurPlanet;

import java.util.Comparator;

/**
 * Comparator class to compare one players Knowledge points against anothers 
 * @param p1
 * @param p2
 */
public class KnowledgePointsComparator implements Comparator<Player>{
	@Override
	public int compare(Player p1, Player p2) {
		return Double.compare(p2.getKnowledgePoints(), p1.getKnowledgePoints());
	}
}
