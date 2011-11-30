<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <msg>
            <xsl:value-of select="/page/data/msg"/>
        </msg>
        <status>
            <xsl:value-of select="/page/data/status"/>
        </status>
    </xsl:template>
</xsl:stylesheet>