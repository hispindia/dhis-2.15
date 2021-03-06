<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:d="http://dhis2.org/schema/dxf/2.0"
        >
    <!--@author Ovidiu Rosu <rosu.ovi@gmail.com>-->
    <xsl:template match="d:filter">
        <div class="filter">
            <h2> <xsl:value-of select="@name" /> </h2>

            <table>
                <tr>
                    <td>ID</td>
                    <td> <xsl:value-of select="@id" /> </td>
                </tr>
                <tr>
                    <td>Created</td>
                    <td> <xsl:value-of select="@created" /> </td>
                </tr>
                <tr>
                    <td>Last Updated</td>
                    <td> <xsl:value-of select="@lastUpdated" /> </td>
                </tr>
                <tr>
                    <td>MetaData UIDs</td>
                    <td> <xsl:value-of select="d:metaDataUids" /> </td>
                </tr>
            </table>

        </div>
    </xsl:template>

</xsl:stylesheet>
