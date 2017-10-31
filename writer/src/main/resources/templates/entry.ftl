<!-- title -->
<h3>${title}</h3>

<!-- intro -->
<#if updateTime??>
<#setting date_format="yyyy년 MM월 dd일">
${updateTime?date}, <#if link??><a href="${link}">${title}</a><#else>${title}</#if> 입니다.<br>
<#else>
현재 국제 사회에서 "<#if link??><a href="${link}">${title}</a><#else>${title}</#if>" 이슈가 급부상중입니다.<br>
</#if>

<!-- main -->
<#if mainArticle??>
<ul style="padding:0px;">
<dl style="overflow:hidden;">
<#if mainArticle.image??>
<dt><a href="${mainArticle.link}"><img src="${mainArticle.image}" style="position:relative; float:left; display: inline; max-width: 240px;"></a></dt>
</#if>
<#if mainArticle.content??>
<dt>최근 ${mainArticle.medium} 에서는  "<a href="${mainArticle.link}">${mainArticle.title}</a>" 의 제목으로 아래와 같은 보도를 전했습니다.</dt>
<dd>${mainArticle.content}</dd>
<#else>
<dt>최근 ${mainArticle.medium} 에서는  "<a href="${mainArticle.link}">${mainArticle.title}</a>" 라고 보도했습니다.</dt>
</#if>
</dl>
</ul>
</#if>

<!-- sub -->
<#if mainArticle??>
<br><br>그 외에도 각종 언론사들은 다음과 같은 보도를 이어나갔습니다.<br><br>
<#else>
<br><br>여러 언론사들은 다음과 같은 보도로 관련 소식을 전했습니다.<br><br>
</#if>

<ul style="padding:0px;">
<#list subArticles as article>
	<li style="list-style:none;">
		<dl style="overflow:hidden;">
			<#if article.image??>
			<dt><a href="${article.link}"><img src="${article.image}" style="position:relative; float:left; display: inline; max-width: 240px;"></a></dt>
			</#if>
			<dt><a href="${article.link}">${article.title}</a> - ${article.medium}</dt>
			<#if article.content??>
			<dd>${article.content}</dd>
			</#if>
		</dl>
	</li>
</#list>
</ul>
