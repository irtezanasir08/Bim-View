package buildingviewer;

import java.util.ArrayList;
import java.util.List;


public class ScheduleGenerator {
	//Each List<SteelMaterial> is for each crane
	//The sequence of SteelMaterial objects in each list represents their placement sequence
	public List<List<SteelMaterial>> getSchedule(int numberOfCranes, List<SteelMaterial> materialList) {
		List<List<SteelMaterial>> schedule = new ArrayList<List<SteelMaterial>>();
		
		for (int i =0; i < numberOfCranes; i++) {
			List<SteelMaterial> jobsForCrane = new ArrayList<SteelMaterial>();
			schedule.add(jobsForCrane);
		}
		
		for (int i=0; i < materialList.size(); i++) {
			schedule.get(i%numberOfCranes).add(materialList.get(i));
		}
		
		return schedule;
	}
}
