package org.hisp.dhis.reporting.dataapproval.action;

/*
 * Copyright (c) 2004-2014, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hisp.dhis.common.comparator.IdentifiableObjectNameComparator;
import org.hisp.dhis.dataelement.CategoryOptionGroup;
import org.hisp.dhis.dataelement.DataElementCategoryService;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.DataSetService;
import org.hisp.dhis.system.util.Filter;
import org.hisp.dhis.system.util.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

public class GetDataApprovalOptionsAction
    implements Action
{
    @Autowired
    private DataElementCategoryService categoryService;

    @Autowired
    private DataSetService dataSetService;
    
    // -------------------------------------------------------------------------
    // Output
    // -------------------------------------------------------------------------

    private List<CategoryOptionGroup> categoryOptionGroups;
    
    public List<CategoryOptionGroup> getCategoryOptionGroups()
    {
        return categoryOptionGroups;
    }

    private List<DataSet> dataSets;

    public List<DataSet> getDataSets()
    {
        return dataSets;
    }
    
    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

    @Override
    public String execute()
        throws Exception
    {
        categoryOptionGroups = new ArrayList<CategoryOptionGroup>( categoryService.getAllCategoryOptionGroups() );
        dataSets = new ArrayList<DataSet>( dataSetService.getAllDataSets() );        
        
        FilterUtils.filter( dataSets, new DataSetApproveDataFilter() );
        
        Collections.sort( categoryOptionGroups, IdentifiableObjectNameComparator.INSTANCE );
        Collections.sort( dataSets, IdentifiableObjectNameComparator.INSTANCE );
        
        return SUCCESS;
    }

    class DataSetApproveDataFilter
        implements Filter<DataSet>
    {
        @Override
        public boolean retain( DataSet dataSet )
        {
            return dataSet != null && dataSet.isApproveData();
        }        
    }
}