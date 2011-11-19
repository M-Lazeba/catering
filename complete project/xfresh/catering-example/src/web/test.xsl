<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


	<xsl:template match="/">
		<html>
			<head>
				<link type="text/css" rel="stylesheet" media="all" href="style.css" />

			<script>
				function vote(id, val) {
					alert(id + " " + val + "!")
				}
				function mover(id) {
					var n = parseInt(id[1], 10)

					var item = id.substring(2, id.length)

					for(var i = 1; i &lt;= n; i++) {
						if (document.getElementById("n" + i + item)) {
							document.getElementById("n" + i + item).src = "select.png"
						}
						if (document.getElementById("b" + i + item)) {
							document.getElementById("b" + i + item).src = "select.png"
						}
					}
				}

				function mout(id) {
					var n = parseInt(id[1], 10)
					var item = id.substring(2, id.length)
					for(var i = 1; i &lt;= n; i++) {
						if (document.getElementById("n" + i + item)) {
							document.getElementById("n" + i + item).src = "nice.png"
						}
						if (document.getElementById("b" + i + item)) {
							document.getElementById("b" + i + item).src = "bad.png"
						}
					}
				}
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
							<td class="search">
								<input class="searchinput" type="text">
									<xsl:attribute name="value"><xsl:value-of select="/page/data/result/request" /></xsl:attribute>
								</input>
							</td>
							<td class="tdlogo">
								<input class="searchsubmit" type="submit" value="Искать!" />
							</td>
						</tr>
					</table>
				</div>



				<table class="maintable">
					<tr>
						<td>
							<xsl:apply-templates select="page/data/result"/>
						</td>
						<td> </td>
					</tr>
					<tr>
						<td class="maintd">
							<xsl:apply-templates select="page/data/item"/>
						</td>
						<td class="tditem">
							<div class="map">
								Здесь карта.
							</div>
							<br />
							<div class="filter">
								<b>Цена:</b><br />
								<div class="pricefilterdiv">
									от <input type="text" class="pricefilter"/> до <input type="text" class="pricefilter"/>
								</div>
								<br />
								<b>Мое местоположение:</b><br />
								<div class="pricefilterdiv">
									<div class="mymap">
										Здесь карта.
									</div>
								</div>
								<br />
								<input type="submit" value="Применить" />
							</div>
						</td>
					</tr>
				</table>

			</body>
		</html>
	</xsl:template>



	<xsl:template match="result">
		<div class="results">
			По запросу "<xsl:value-of select="request" />" найдено позиций: <xsl:value-of select="count" />. <br /> Сортировать по цене
	            (<a><xsl:attribute name="href">search.xml?req=<xsl:value-of select="/page/result/request" />&amp;order=price</xsl:attribute>↑</a>
	         <a><xsl:attribute name="href">search.xml?
					req=<xsl:value-of select="/page/result/request" />
					&amp;order=-price
				</xsl:attribute>↓</a>), по рейтигну
				(<a><xsl:attribute name="href">search.xml?
					req=<xsl:value-of select="/page/result/request" />
					&amp;order=ratio
				</xsl:attribute>↑</a>
	         <a><xsl:attribute name="href">search.xml?
					req=<xsl:value-of select="/page/result/request" />
					&amp;order=-ratio
				</xsl:attribute>↓</a>).
		</div>
	</xsl:template>


	<xsl:template match="item">
		<div class="item">
			<xsl:attribute name="id">
				<xsl:value-of select="id" />
			</xsl:attribute>
			<table class="tableitem">
				<tr>
					<td>
						<table class="tablethumb">
							<tr>
								<td class="tdthumb">
									<xsl:if test="haspic = 1">
										<a>
											<xsl:attribute name="href"><xsl:value-of select="url" /></xsl:attribute>
											<img class="thumb">
												<xsl:attribute name="src">media/thumb/<xsl:value-of select="id" />.jpg</xsl:attribute>
											</img>
										</a>
									</xsl:if>
									<xsl:if test="haspic != 1">
										<a>
											<xsl:attribute name="href"><xsl:value-of select="url" /></xsl:attribute>
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
								<xsl:attribute name="href"><xsl:value-of select="url" /></xsl:attribute>
								<xsl:value-of select="title"/>
							</a>
							<xsl:for-each select="tags/tag">
								<sup>
				  					<xsl:text> </xsl:text>
									<a>
										<xsl:attribute name="href">search.xml?type=tag&amp;id=<xsl:value-of select="id" /></xsl:attribute>
										<span class="tag">
		  									<xsl:value-of select="value"/>
					  					</span>
 									</a>
	 						</sup>
							</xsl:for-each>
						</h2>
						<xsl:apply-templates select="price" />
						<xsl:apply-templates select="description" />
					</td>
				</tr>
			</table>
		</div>
	</xsl:template>


	<xsl:template match="price">
		<span class="price">
			<xsl:value-of select="."/>
			<strike><xsl:text>Р</xsl:text></strike>
		</span>
		<span class="place">
			<xsl:text> - </xsl:text>
			<xsl:value-of select="../place/type" />
			<xsl:text> </xsl:text>
			<a>
				<xsl:attribute name="href">place.xml?id=<xsl:value-of select="../place/id" /></xsl:attribute>
				<xsl:value-of select="../place/name" />
			</a>
		</span>
	</xsl:template>

	<xsl:template match="description">
		<div class="description">
			<xsl:value-of select="." />
		</div>
	</xsl:template>

	<xsl:template match="ratio">
	   	<xsl:choose>

   	   <xsl:when test=". &gt; 4">
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 1)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n1_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 2)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n2_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 3)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n3_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 4)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n4_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 5)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n5_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   	      </xsl:when>

   	   <xsl:when test=". &gt; 3">
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 1)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n1_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 2)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n2_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 3)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n3_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 4)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n4_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>




   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 5)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b5_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


	      </xsl:when>

   	   <xsl:when test=". &gt; 2">
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 1)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n1_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 2)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n2_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 3)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n3_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 4)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b4_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 5)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b5_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


	      </xsl:when>

   	   <xsl:when test=". &gt; 1">
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 1)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n1_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>
   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 2)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n2_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>



   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 3)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b3_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 4)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b4_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 5)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b5_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


	      </xsl:when>

      	<xsl:otherwise>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 1)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("n1_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="nice.png">
   	   		<xsl:attribute name="id">n1_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>




   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 2)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b2_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b2_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>




   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 3)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b3_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b3_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 4)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b4_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b4_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>

   	   <a>
   	   	<xsl:attribute name="onclick">
   	   		vote(<xsl:value-of select="../id" />, 5)
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseover">
   	   		mover("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<xsl:attribute name="onmouseout">
   	   		mout("b5_<xsl:value-of select="../id" />")
   	   	</xsl:attribute>
   	   	<img src="bad.png">
   	   		<xsl:attribute name="id">b5_<xsl:value-of select="../id" /></xsl:attribute>
   	   	</img>
   	   </a>


   	   </xsl:otherwise>

      	</xsl:choose>
	</xsl:template>

</xsl:stylesheet>