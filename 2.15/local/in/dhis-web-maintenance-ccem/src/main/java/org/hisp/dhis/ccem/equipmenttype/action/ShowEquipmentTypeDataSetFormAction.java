package org.hisp.dhis.ccem.equipmenttype.action;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.coldchain.equipment.EquipmentType;
import org.hisp.dhis.coldchain.equipment.EquipmentTypeService;
import org.hisp.dhis.dataset.DataSet;

import com.opensymphony.xwork2.Action;

public class ShowEquipmentTypeDataSetFormAction implements Action
{

    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------
    private EquipmentTypeService equipmentTypeService;

    public void setEquipmentTypeService( EquipmentTypeService equipmentTypeService )
    {
        this.equipmentTypeService = equipmentTypeService;
    }

    // -------------------------------------------------------------------------
    // Input/Output
    // -------------------------------------------------------------------------
    private String id;

    public void setId( String id )
    {
        this.id = id;
    }
    
    private EquipmentType equipmentType;
    
    public EquipmentType getEquipmentType()
    {
        return equipmentType;
    }
    
    private List<DataSet> selEquipmentTypeDataSets;
    
    public List<DataSet> getSelEquipmentTypeDataSets()
    {
        return selEquipmentTypeDataSets;
    }

    private List<EquipmentType> equipmentTypeList;
    
    public List<EquipmentType> getEquipmentTypeList()
    {
        return equipmentTypeList;
    }


    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------
    public String execute() throws Exception
    {
        equipmentType = equipmentTypeService.getEquipmentType( Integer.parseInt( id ) );
        
        selEquipmentTypeDataSets = new ArrayList<DataSet>(  equipmentType.getDataSets() );
        
        equipmentTypeList = new ArrayList<EquipmentType>();
        
        for( EquipmentType equipmentType : equipmentTypeService.getAllEquipmentTypes() ) 
        {
            if(  equipmentType.getDataSets() != null && equipmentType.getDataSets().size() != 0  )
            {
                equipmentTypeList.add( equipmentType );
            }
        }
        
        return SUCCESS;        
    }
}
