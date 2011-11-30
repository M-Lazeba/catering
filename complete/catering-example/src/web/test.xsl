<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <html>
            <head>
                <link type="text/css" rel="stylesheet" media="all" href="/styles/style.css"/>
                <link type="text/css" rel="stylesheet" media="all" href="/styles/jquery.rating.css"/>
                <script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>
                <script type="text/javascript" src="/js/jquery.rating.js"></script>
                <script>

                    $(function(){
                    $('div.rating').rating({
                    fx: 'full',
                    image: 'images/stars.png',
                    loader: 'images/ajax-loader.gif',
                    width: 22,
                    url: 'rating.xml'
                    });
                    })

                </script>
            </head>
            <body>
                <div class="header">
                    <table class="tablelogo">
                        <tr>
                            <td class="tdlogo">
                                <a href="/">
                                    <img src="logo.png" class="logo" alt="Catering!"/>
                                </a>
                            </td>
                            <td class="tdlogo">
                                <a href="/">
                                    <h1>
                                        Catering!
                                    </h1>
                                </a>
                            </td>
                            <form action="">
                                <td class="search">
                                    <input class="searchinput" type="text" name="req">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="/page/data/result/request"/>
                                        </xsl:attribute>
                                    </input>
                                    <input type="hidden" name="order">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="/page/data/result/order"/>
                                        </xsl:attribute>
                                    </input>

                                </td>
                                <td class="tdlogo">
                                    <input class="searchsubmit" type="submit" value="Искать!"/>
                                </td>
                            </form>
                        </tr>
                    </table>
                </div>


                <table class="maintable">
                    <tr>
                        <td>
                            <xsl:apply-templates select="page/data/result"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="maintd">
                            <xsl:apply-templates select="page/data/item"/>
                        </td>
                        <td class="tditem">
                            <div class="map">
                                Здесь карта.
                            </div>
                            <br/>
                            <div class="filter">
                                <form action="">
                                    <b>Цена:</b>
                                    <br/>
                                    <div class="pricefilterdiv">
                                        от
                                        <input type="text" class="pricefilter" name="min">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="/page/data/result/min"/>
                                            </xsl:attribute>
                                        </input>
                                        до
                                        <input type="text" class="pricefilter" name="max">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="/page/data/result/max"/>
                                            </xsl:attribute>
                                        </input>
                                    </div>
                                    <br/>
                                    <b>Мое местоположение:</b>
                                    <br/>
                                    <div class="pricefilterdiv">
                                        <div class="mymap">
                                            Здесь карта.
                                        </div>
                                    </div>
                                    <br/>
                                    <input type="hidden" name="req">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="/page/data/result/request"/>
                                        </xsl:attribute>
                                    </input>
                                    <input type="hidden" name="order">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="/page/data/result/order"/>
                                        </xsl:attribute>
                                    </input>
                                    <input type="hidden" name="type">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="/page/data/result/type"/>
                                        </xsl:attribute>
                                    </input>
                                    <input type="submit" value="Применить"/>

                                </form>
                            </div>
                        </td>
                    </tr>
                </table>

            </body>
        </html>
    </xsl:template>


    <xsl:template match="result">
        <div class="results">
            По запросу "<xsl:value-of select="request"/>" найдено позиций: <xsl:value-of select="count"/>.
            <br/>
            Сортировать по цене
            (
            <a>
                <xsl:attribute name="href">search.xml?req=<xsl:value-of select="request"/>&amp;min=<xsl:value-of
                        select="min"/>&amp;max=<xsl:value-of select="max"/>&amp;order=price
                </xsl:attribute>
                ↑
            </a>
            <a>
                <xsl:attribute name="href">search.xml?req=<xsl:value-of select="request"/>&amp;min=<xsl:value-of
                        select="min"/>&amp;max=<xsl:value-of select="max"/>&amp;order=-price
                </xsl:attribute>
                ↓
            </a>
            ), по рейтигну
            (
            <a>
                <xsl:attribute name="href">search.xml?req=<xsl:value-of select="request"/>&amp;min=<xsl:value-of
                        select="min"/>&amp;max=<xsl:value-of select="max"/>&amp;order=ratio
                </xsl:attribute>
                ↑
            </a>
            <a>
                <xsl:attribute name="href">search.xml?req=<xsl:value-of select="request"/>&amp;min=<xsl:value-of
                        select="min"/>&amp;max=<xsl:value-of select="max"/>&amp;order=-ratio
                </xsl:attribute>
                ↓
            </a>
            ).
        </div>
    </xsl:template>


    <xsl:template match="item">
        <div class="item">
            <xsl:attribute name="id">
                <xsl:value-of select="id"/>
            </xsl:attribute>
            <table class="tableitem">
                <tr>
                    <td>
                        <table class="tablethumb">
                            <tr>
                                <td class="tdthumb">
                                    <xsl:if test="haspic = 1">
                                        <a>
                                            <xsl:attribute name="href">
                                                <xsl:value-of select="url"/>
                                            </xsl:attribute>
                                            <img class="thumb">
                                                <xsl:attribute name="src">media/thumb/<xsl:value-of select="id"/>.jpg
                                                </xsl:attribute>
                                            </img>
                                        </a>
                                    </xsl:if>
                                    <xsl:if test="haspic != 1">
                                        <a>
                                            <xsl:attribute name="href">
                                                <xsl:value-of select="url"/>
                                            </xsl:attribute>
                                            <img class="thumb">
                                                <xsl:attribute name="src">media/thumb/default.png</xsl:attribute>
                                            </img>
                                        </a>
                                    </xsl:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdthumb">
                                    <xsl:apply-templates select="ratio"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="tditem">
                        <h2>
                            <a>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="url"/>
                                </xsl:attribute>
                                <xsl:value-of select="title"/>
                            </a>
                            <xsl:for-each select="tags/tag">
                                <sup>
                                    <xsl:text> </xsl:text>
                                    <a>
                                        <xsl:attribute name="href">search.xml?type=tag&amp;id=<xsl:value-of
                                                select="id"/>
                                        </xsl:attribute>
                                        <span class="tag">
                                            <xsl:value-of select="value"/>
                                        </span>
                                    </a>
                                </sup>
                            </xsl:for-each>
                        </h2>
                        <xsl:apply-templates select="price"/>
                        <xsl:apply-templates select="description"/>
                    </td>
                </tr>
            </table>
        </div>
    </xsl:template>


    <xsl:template match="price">
        <span class="price">
            <xsl:value-of select="."/>
            <strike>
                <xsl:text>Р</xsl:text>
            </strike>
        </span>
        <span class="place">
            <xsl:text> - </xsl:text>
            <xsl:value-of select="../place/type"/>
            <xsl:text> </xsl:text>
            <a>
                <xsl:attribute name="href">place.xml?id=<xsl:value-of select="../place/id"/>
                </xsl:attribute>
                <xsl:value-of select="../place/name"/>
            </a>
        </span>
    </xsl:template>

    <xsl:template match="description">
        <div class="description">
            <xsl:value-of select="."/>
        </div>
    </xsl:template>

    <xsl:template match="ratio">
        <div class="rating">
            <input type="hidden" class="val" value="2.75"/>
            <input type="hidden" class="vote-ID">
                <xsl:attribute name="value">
                    <xsl:value-of select="../id"/>
                </xsl:attribute>
            </input>
            <input type="hidden" class="votes" value="3"/>
        </div>
    </xsl:template>

</xsl:stylesheet>