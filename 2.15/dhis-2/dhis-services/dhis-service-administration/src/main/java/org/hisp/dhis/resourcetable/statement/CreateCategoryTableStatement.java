package org.hisp.dhis.resourcetable.statement;

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

import java.util.List;

import org.amplecode.quick.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hisp.dhis.dataelement.DataElementCategory;

/**
 * @author Lars Helge Overland
 */
public class CreateCategoryTableStatement
    implements Statement
{
    private static final Log log = LogFactory.getLog( CreateCategoryTableStatement.class );
    
    private static final String LONG_TEXT_COLUMN_TYPE = "VARCHAR (250)";
    
    public static final String TABLE_NAME = "_categorystructure";
    
    private List<DataElementCategory> categories;

    private String quote;
    
    public CreateCategoryTableStatement( List<DataElementCategory> categories, String quote )
    {
        this.categories = categories;
        this.quote = quote;
    }    

    public String getStatement()
    {
        String statement = "CREATE TABLE " + TABLE_NAME + " ( " +
            "categoryoptioncomboid " + NUMERIC_COLUMN_TYPE + SEPARATOR +
            "categoryoptioncomboname " + LONG_TEXT_COLUMN_TYPE + SEPARATOR;
        
        for ( DataElementCategory category : categories )
        {
            statement += quote + category.getName() + quote + SPACE + LONG_TEXT_COLUMN_TYPE + SEPARATOR;
            statement += quote + category.getUid() + quote + SPACE + "CHARACTER(11)" + SEPARATOR;
        }

        statement += "PRIMARY KEY ( categoryoptioncomboid ) )";
                
        log.info( "Create category structure table SQL: " + statement );
        
        return statement;
    }        
}
