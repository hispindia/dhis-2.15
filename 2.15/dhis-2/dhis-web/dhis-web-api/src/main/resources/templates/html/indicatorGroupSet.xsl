<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:d="http://dhis2.org/schema/dxf/2.0"
    >

  <xsl:template match="d:indicatorGroupSet">
    <div class="indicatorGroupSet">
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
          <td>Code</td>
          <td> <xsl:value-of select="@code" /> </td>
        </tr>
        <tr>
          <td>Compulsory</td>
          <td> <xsl:value-of select="d:compulsory" /> </td>
        </tr>
      </table>

      <xsl:apply-templates select="d:indicatorGroups" mode="short"/>
    </div>
  </xsl:template>

  <xsl:template match="d:indicatorGroupSet" mode="short">
    <h3>Indicator Group Set</h3>
    <table class="indicatorGroupSet">
      <xsl:apply-templates select="." mode="row"/>
    </table>
  </xsl:template>

</xsl:stylesheet>
