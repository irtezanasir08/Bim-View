package buildingviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.Parent;


public class ScheduleGenerator {
	//Each List<SteelMaterial> is for each crane
	//The sequence of SteelMaterial objects in each list represents their placement sequence
	public List<List<SteelMaterial>> getSchedule(int numberOfCranes, List<SteelMaterial> materialList) {
		List<List<SteelMaterial>> schedule = new ArrayList<List<SteelMaterial>>();
		MaterialDependencyGenerator materialDependencyGenerator = new MaterialDependencyGenerator();
		
		List<MaterialDependency> materialDependencies = materialDependencyGenerator.getMaterialDependency(materialList);
		for( MaterialDependency ma : materialDependencies) {
        	System.out.println("Without no reason: " + ma.materialId + " "+ ma.materialType + " " + ma.materialParentId + " " + ma.prerequisiteMaterialList.size());
        }

//		for (MaterialDependency dependency : materialDependencies) {
//			if (dependency.prerequisiteMaterialList.size() > 0) {
//				System.out.println("\n\n\n material: "+dependency.materialId + " " + dependency.materialType + "\n ");
//			
//				for (MaterialDependency child : dependency.prerequisiteMaterialList) {
//					System.out.print(" child: " + child.materialId + " " + child.materialType + " ");
//				}
//			}
//		}
		HashMap<Integer,ArrayList<MaterialDependency>> independentMaterialSequences = getListofIndependentSequences(materialDependencies);
		System.out.println("\n\n khoda " + independentMaterialSequences.size() + " \n\n");
		for (int i =0; i < numberOfCranes; i++) {
			List<SteelMaterial> jobsForCrane = new ArrayList<SteelMaterial>();
			schedule.add(jobsForCrane);
		}
		
//		for ( independentMaterialSequences.keySet().size(); i++) {
//			schedule.get(i%numberOfCranes).add(materialList.get(i));
//		}
		
		Iterator it = independentMaterialSequences.entrySet().iterator();
		int count = 0;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ArrayList<MaterialDependency> sch = (ArrayList<MaterialDependency>) pair.getValue();
	        for (MaterialDependency matD : sch) {
	        	SteelMaterial steelMaterial = null;
	        	for (SteelMaterial steelMat : materialList) {
	        		if (steelMat.id == matD.materialId) {
	        			steelMaterial = steelMat;
	        		}
	        	}
	        	schedule.get(count%numberOfCranes).add(steelMaterial);
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	        count++;
	    }
		
		return schedule;
	}
	
	private ArrayList<MaterialDependency> getLegalSequence(ArrayList<MaterialDependency> materialDependencies, ArrayList<MaterialDependency> generatedList , MaterialDependency currentItem) {
		
		//System.out.println("Rafa: "+generatedList.size());
		if (currentItem.prerequisiteMaterialList.size() == 0) {
			//if (!generatedList.contains(currentItem)) {
				generatedList.add(currentItem);
			//}
			return generatedList;
		}
		
		//List<List<MaterialDependency>> childLists = new ArrayList<List<MaterialDependency>>();
		for (MaterialDependency materialDependency : currentItem.prerequisiteMaterialList) {
			generatedList = getLegalSequence(materialDependencies, generatedList, materialDependency);
			//generatedList.add(temp.get(temp.size() - 1));
		}
		
		//if (!generatedList.contains(currentItem)) {
			generatedList.add(currentItem);
		//}
		
		return generatedList;
	}
	
	private HashMap<Integer,ArrayList<MaterialDependency>> getListofIndependentSequences(List<MaterialDependency> materialDependencies) {
		HashMap<Integer,ArrayList<MaterialDependency>> multiMap = new HashMap<Integer, ArrayList<MaterialDependency>>();
		
		for (MaterialDependency materialDependency : materialDependencies) {
			//MaterialDependency parent;
			MaterialDependency pointerMaterialDependency;
			
			pointerMaterialDependency = materialDependency;
			
			while(true) {
				if (pointerMaterialDependency.materialParentId == -1) {
					if (multiMap.containsKey(pointerMaterialDependency.materialId)) {
						multiMap.get(pointerMaterialDependency.materialId).add(materialDependency);
					} else {
						ArrayList<MaterialDependency> matList = new ArrayList<>();
						matList.add(materialDependency);
						multiMap.put(pointerMaterialDependency.materialId, matList);
					}
					break;
				}
				for (MaterialDependency m : materialDependencies) {
					if (m.materialId == pointerMaterialDependency.materialParentId)	{
						pointerMaterialDependency = m;
					}
				}
				
				
			}
		}
		
//		Iterator it = multiMap.entrySet().iterator();
//		while (it.hasNext()) {
//	        Map.Entry pair = (Map.Entry)it.next();
//	        ArrayList<MaterialDependency> sch = (ArrayList<MaterialDependency>) pair.getValue();
//	        MaterialDependency root = null;
//	        MaterialDependency matD = sch.get(0);
//	        
//	        while(true) {
//	        	if (matD.materialParent == null) {
//	        		root = matD;
//	       			break;
//	       		}
//	       		root = matD.materialParent;
//	       	}
//	        
//	        ArrayList<MaterialDependency> mats = new ArrayList<>();
//	        
//	        for( MaterialDependency ma : sch) {
//	        	//System.out.println("Without no reason: " + ma.materialId + " "+ ma.materialType + " " + ma.materialParent + " " + ma.prerequisiteMaterialList.size());
//	        }
//	        
//	        multiMap.put((Integer) pair.getKey(), getLegalSequence(sch, new ArrayList<>(), root));
//	        it.remove(); // avoids a ConcurrentModificationException
//	        
//		}
		//List<List<Integer>> listOfIndependentSequences = new ArrayList<List<Integer>>();
		
		//for ()
		
		return multiMap;
		
	}
}
