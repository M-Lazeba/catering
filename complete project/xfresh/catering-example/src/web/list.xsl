<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <html>
            <body>
                <h1>Catering!</h1>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="item">
        <hr/>
        <h2>
            <xsl:value-of select="title"/>
            <xsl:for-each select="tag">
                <sup>
                    <xsl:text>&#160;</xsl:text>
                    <span style="background-color:#0000ff; color:#ffffff; font-size:10pt">
                        <xsl:value-of select="."/>
                    </span>
                </sup>
            </xsl:for-each>
        </h2>
        <xsl:apply-templates select="price"/>
        <br/>
        <xsl:apply-templates select="ratio"/>
    </xsl:template>


    <xsl:template match="price">
        <xsl:value-of select="."/>
        <xsl:text> рублей</xsl:text>
    </xsl:template>

    <xsl:template match="ratio">
        <xsl:text>Рейтинг: </xsl:text>
        <font size="28">
            <xsl:choose>

                <xsl:when test=". > 4">
                    <xsl:text>*****</xsl:text>
                </xsl:when>

                <xsl:when test=". > 3">
                    <xsl:text>****</xsl:text>
                </xsl:when>

                <xsl:when test=". > 2">
                    <xsl:text>***</xsl:text>
                </xsl:when>

                <xsl:when test=". > 1">
                    <xsl:text>**</xsl:text>
                </xsl:when>

                <xsl:otherwise>
                    <xsl:text>*</xsl:text>
                </xsl:otherwise>

            </xsl:choose>
        </font>
    </xsl:template>

</xsl:stylesheet>