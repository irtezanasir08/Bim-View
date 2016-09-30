package buildingviewer;

import java.util.ArrayList;
import java.util.List;

public class MaterialDependency {
	public int materialId;
	public int materialParentId = -1;
	public int materialType;
	public List<MaterialDependency> prerequisiteMaterialList;
	
	public MaterialDependency() {
		// TODO Auto-generated constructor stub
		prerequisiteMaterialList = new ArrayList<>();
		//materialParent = null;
	}
}
