<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0"
  xmlns:lxslt="http://xml.apache.org/xslt"
  xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
  extension-element-prefixes="redirect">

  <xsl:output method="html" indent="yes"/>

  <xsl:param name="output.dir" select="'.'" />
  <xsl:param name="timestamp" select="'.'" />

  <xsl:template match="www">
    <xsl:for-each select="document">
      <xsl:message>
        <xsl:value-of select="$output.dir"/>
        <xsl:value-of select="@id"/>
      </xsl:message>
      <redirect:write file="{$output.dir}/{@id}.html">
        <xsl:call-template name="document.html" />
      </redirect:write>
    </xsl:for-each>
  </xsl:template>

  <!--
       The base page for all documents
       -->
  <xsl:template name="document.html">
    <html>
      <head>
        <xsl:call-template name="stylesheet.css" />
        <title><xsl:value-of select="properties/title"/></title>
      </head>
      <body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
        <a name="TOP"/>
        <table width="800" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td>
              <img src="images/banner.jpg" width="800" height="200" />
            </td>
          </tr>
          <tr>
            <td align="center" valign="middle" class="banner">
              <xsl:call-template name="document.links" />
            </td>
          </tr>
          <tr height="45">
            <td>
            </td>
          </tr>
          <tr>
            <td>
              <table width="800">
                <tr>
                  <td width="50">
                  </td>
                  <td width="10">.
                  </td>
                  <td width="580">
                    <xsl:apply-templates select="body"/>
                    <div align="right">
                      <a href="#TOP">top</a>
                    </div>
                  </td>
                  <td width="10">.
                  </td>
                  <td width="150">
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td align="right" valign="middle" class="banner">
              Copyright 2003 L2FProd.com
              <br />
              Generated on <xsl:value-of select="$timestamp"/>
            </td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="sitemap">
    <xsl:for-each select="//document[not(.//sitemap)]">
      <ul>
        <h4>
          <a href="{@id}.html"><xsl:value-of select="properties/title"></xsl:value-of></a>
        </h4>
        <xsl:apply-templates select="body/section" mode="sitemap"/>
      </ul>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="section" mode="sitemap">
    <li>
      <a href="">
        <xsl:attribute name="href"><xsl:value-of select="ancestor-or-self::document/@id"/>.html#<xsl:value-of select="title"/></xsl:attribute>
        <xsl:value-of select="title"></xsl:value-of></a>
      <ul>
        <xsl:apply-templates select="section" mode="sitemap"/>
      </ul>
    </li>
  </xsl:template>

  <xsl:template name="document.links">
:: <xsl:for-each select="//document">
      <a class="banner" href="{@id}.html">
        <xsl:value-of select="properties/title"/>
      </a> ::
    </xsl:for-each>
  </xsl:template>

  <!--
       All Titles
       -->
  <xsl:template match="section/section/title">
    <h4><xsl:value-of select="."/></h4>
  </xsl:template>

  <xsl:template match="body/section/title">
    <h3><xsl:value-of select="."/></h3>
  </xsl:template>

  <xsl:template match="title">
    <h4><xsl:value-of select="."/></h4>
  </xsl:template>

  <xsl:template match="section">
    <a name="{title}"/>
    <xsl:apply-templates/>
  </xsl:template>

  <!--
       The stylesheet used by all pages
       -->
  <xsl:template name="stylesheet.css">
    <style type="text/css">
body {
      font:normal 75% verdana,arial,helvetica;
      color: #000000;
}
table tr td {
      font:normal 75% verdana,arial,helvetica;
      color: #000000;
}
td.banner {
      color: #FFFFFF;
      background: #002490;
}
a.banner {
      font:normal 70% verdana,arial,helvetica;
      color: #FFFFFF;
      text-decoration: none;
}
a:visited.banner {
      font:normal 70% verdana,arial,helvetica;
      color: #FFFFFF;
      text-decoration: none;
}
    </style>
  </xsl:template>

  <!-- copy all -->
  <xsl:template match="*">
    <xsl:copy>
      <xsl:copy-of select="attribute::*[. != '']"/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
