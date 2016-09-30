package buildingviewer;

import java.util.ArrayList;
import java.util.List;

public class MaterialDependencyGenerator {
	public List<MaterialDependency> getMaterialDependency(List<SteelMaterial> materialList) {
		List<MaterialDependency> materialDependencies = new ArrayList<>();
		
		for (SteelMaterial material : materialList) {
			MaterialDependency materialDependency = new MaterialDependency();
			
			materialDependency.materialId = material.id;
			materialDependency.materialType = material.materialType;
			
			List<Integer> assigned = new ArrayList<>();
			
			if (material.materialType == Constants.MATERIAL_TYPE_COLUMN) {
				int count = 0;
				for (SteelMaterial childMaterial : materialList) {
					if (childMaterial.materialType == Constants.MATERIAL_TYPE_FRAMING) {
						
						count ++;
						if (!assigned.contains(childMaterial.id)) {
							MaterialDependency childMaterialDependency = new MaterialDependency();
							childMaterialDependency.materialId = childMaterial.id;
							childMaterialDependency.materialType = childMaterial.materialType;
							childMaterialDependency.materialParentId = 5;
							materialDependency.prerequisiteMaterialList.add(childMaterialDependency);
						}
						
						if (count == 4) 
							break;
					}
				}
			}
			
			materialDependencies.add(materialDependency);
		}

		return materialDependencies;
	}
}
