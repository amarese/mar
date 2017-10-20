package pe.mar.writer.news.collector;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.mar.writer.news.collector.NateNewsCollector;
import pe.mar.writer.news.collector.NewsCollectorBase.News;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NateNewsCollectorTest {
	@Inject
	NateNewsCollector collector;

	@Test
	public void splitBody() throws Exception {
		String expected = "<h4>'\u7F8E-\uC774\uB780 \uD575 \uD569\uC758' \uAC08\uB4F1</h4>\t<div class=\"clusterType1\">\r\n\t\t<!-- \uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\t\t<div class=\"mlt01\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://news.nate.com/view/20171017n07710?mid=n0500\" class=\"lt1\" onclick=\"ndrclick('RMG01');\" >\r\n\t\t\t\t<span class=\"ib\">\r\n\t\t\t\t\t<img src=\"http://thumbnews.nateimg.co.kr/news90/http://news.nateimg.co.kr/orgImg/yt/2017/10/17/C0A8CA3D0000015F24859BF60019EC2D_P2.jpeg\" onerror=\"blankImg(this,0,0)\" alt=\"\" />\r\n\t\t\t\t</span>\t\t\t\t<span class=\"tb\">\r\n\t\t\t\t\t\t\t\t\t\t<strong class=\"tit\">\uD2B8\uB7FC\uD504 &quot;\uC774\uB780 \uD575\uD569\uC758 '\uC644\uC804\uD55C \uC885\uACB0' \uAC00\uB2A5\uC131 \uC788\uB2E4&quot;</strong>\r\n\t\t\t\t\t(\uC11C\uC6B8=\uC5F0\uD569\uB274\uC2A4) \uAD8C\uD61C\uC9C4 \uAE30\uC790 = \uB3C4\uB110\uB4DC \uD2B8\uB7FC\uD504 \uBBF8\uAD6D \uB300\uD1B5\uB839\uC774 \uC774\uB780 \uD575\uD569\uC758 \uBD88\uC778\uC99D\uC744 \uB118\uC5B4 '\uC644\uC804\uD55C \uC885\uACB0'(total termination) \uAC00\uB2A5\uC131\uC744 \uC2DC\uC0AC\uD588\uB2E4.     \uC678\uC2E0\uC5D0 \uB530\uB974\uBA74 \uD2B8\uB7FC\uD504 \uB300\uD1B5\uB839\uC740 ...\t\t\t\t</span>\r\n\t\t\t</a>\r\n\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4<em>2017-10-17</em></span>\r\n\t\t</div><!-- //\uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\r\n\t\t<!-- \uAD00\uB828\uAE30\uC0AC \uB9AC\uC2A4\uD2B8 -->\r\n\t\t<ul class=\"mduList1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171015n02330?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uC774\uB780 \uD575\uD611\uC815 '\uBD88\uC778\uC99D' \uD6C4\uC18D\uC785\uBC95 \uB193\uACE0 \uACF5\uD654\uB2F9 \uB09C\uAE30\uB958</a>\r\n\t\t\t\t<span class=\"medium\">KBS\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n14430?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504 '\uC774\uB780 \uD575\uD611\uC815 \uBD88\uC778\uC99D'\u2026\uAD6D\uC81C\uC0AC\uD68C \uBC18\uC751 \uC5C7\uAC08\uB824</a>\r\n\t\t\t\t<span class=\"medium\">JTBC</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n09301?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\u7F8E \uBE7C\uACE0 \uBAA8\uB450 \uC774\uB780\uD575\uD569\uC758 \uC9C0\uC9C0\u2026\uC2EC\uD310\uAE30\uAD6C\uB3C4 \"\uD2B8\uB7FC\uD504 \uD2C0\uB838\uB2E4\"(\uC885\uD569)</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n10282?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD5E4\uC77C\uB9AC \uC720\uC5D4\uB300\uC0AC\uAC00 '\uC774\uB780\uD575\uD569\uC758 \uBB34\uB825\uD654' \uCD1D\uB300 \uBA68\uB2E4</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n03465?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504 \uBD88\uC778\uC99D \uC120\uC5B8\uC5D0 \uAD6D\uC81C\uC0AC\uD68C \uBC18\uBC1C\u2026IAEA \"\uC774\uB780, \uD575\uD611\uC815 \uC900\uC218\"</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n01578?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504, \uC774\uB780 \uD575\uD611\uC815 \uC778\uC99D \uAC70\uBD80\u2026\uC5B8\uC81C\uB4E0 \uD0C8\uD1F4 \uACBD\uACE0(\uC885\uD569)</a>\r\n\t\t\t\t<span class=\"medium\">\uB274\uC2A41</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n00508?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uC774\uB780\uD575\uD611\uC815 \uAE30\uB85C\u2026\uD2B8\uB7FC\uD504\uC758 \uB05D\uC5C6\uB294 '\uC624\uBC14\uB9C8 \uC9C0\uC6B0\uAE30'</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t</div>";
		List<News> news = collector.splitBody(
				"<!-- \uAE30\uBCF8\uD615 -->\r\n\t\t\t<!-- Cluster : clusterType1 [\uAE30\uBCF8\uD615] -->\r\n<div id=\"cid573180\">\r\n<div class=\"mduCluster\">\r\n\t<h4>'\u7F8E-\uC774\uB780 \uD575 \uD569\uC758' \uAC08\uB4F1</h4>\t<div class=\"clusterType1\">\r\n\t\t<!-- \uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\t\t<div class=\"mlt01\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://news.nate.com/view/20171017n07710?mid=n0500\" class=\"lt1\" onclick=\"ndrclick('RMG01');\" >\r\n\t\t\t\t<span class=\"ib\">\r\n\t\t\t\t\t<img src=\"http://thumbnews.nateimg.co.kr/news90/http://news.nateimg.co.kr/orgImg/yt/2017/10/17/C0A8CA3D0000015F24859BF60019EC2D_P2.jpeg\" onerror=\"blankImg(this,0,0)\" alt=\"\" />\r\n\t\t\t\t</span>\t\t\t\t<span class=\"tb\">\r\n\t\t\t\t\t\t\t\t\t\t<strong class=\"tit\">\uD2B8\uB7FC\uD504 &quot;\uC774\uB780 \uD575\uD569\uC758 '\uC644\uC804\uD55C \uC885\uACB0' \uAC00\uB2A5\uC131 \uC788\uB2E4&quot;</strong>\r\n\t\t\t\t\t(\uC11C\uC6B8=\uC5F0\uD569\uB274\uC2A4) \uAD8C\uD61C\uC9C4 \uAE30\uC790 = \uB3C4\uB110\uB4DC \uD2B8\uB7FC\uD504 \uBBF8\uAD6D \uB300\uD1B5\uB839\uC774 \uC774\uB780 \uD575\uD569\uC758 \uBD88\uC778\uC99D\uC744 \uB118\uC5B4 '\uC644\uC804\uD55C \uC885\uACB0'(total termination) \uAC00\uB2A5\uC131\uC744 \uC2DC\uC0AC\uD588\uB2E4.     \uC678\uC2E0\uC5D0 \uB530\uB974\uBA74 \uD2B8\uB7FC\uD504 \uB300\uD1B5\uB839\uC740 ...\t\t\t\t</span>\r\n\t\t\t</a>\r\n\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4<em>2017-10-17</em></span>\r\n\t\t</div><!-- //\uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\r\n\t\t<!-- \uAD00\uB828\uAE30\uC0AC \uB9AC\uC2A4\uD2B8 -->\r\n\t\t<ul class=\"mduList1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171015n02330?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uC774\uB780 \uD575\uD611\uC815 '\uBD88\uC778\uC99D' \uD6C4\uC18D\uC785\uBC95 \uB193\uACE0 \uACF5\uD654\uB2F9 \uB09C\uAE30\uB958</a>\r\n\t\t\t\t<span class=\"medium\">KBS\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n14430?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504 '\uC774\uB780 \uD575\uD611\uC815 \uBD88\uC778\uC99D'\u2026\uAD6D\uC81C\uC0AC\uD68C \uBC18\uC751 \uC5C7\uAC08\uB824</a>\r\n\t\t\t\t<span class=\"medium\">JTBC</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n09301?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\u7F8E \uBE7C\uACE0 \uBAA8\uB450 \uC774\uB780\uD575\uD569\uC758 \uC9C0\uC9C0\u2026\uC2EC\uD310\uAE30\uAD6C\uB3C4 \"\uD2B8\uB7FC\uD504 \uD2C0\uB838\uB2E4\"(\uC885\uD569)</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n10282?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD5E4\uC77C\uB9AC \uC720\uC5D4\uB300\uC0AC\uAC00 '\uC774\uB780\uD575\uD569\uC758 \uBB34\uB825\uD654' \uCD1D\uB300 \uBA68\uB2E4</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n03465?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504 \uBD88\uC778\uC99D \uC120\uC5B8\uC5D0 \uAD6D\uC81C\uC0AC\uD68C \uBC18\uBC1C\u2026IAEA \"\uC774\uB780, \uD575\uD611\uC815 \uC900\uC218\"</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n01578?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504, \uC774\uB780 \uD575\uD611\uC815 \uC778\uC99D \uAC70\uBD80\u2026\uC5B8\uC81C\uB4E0 \uD0C8\uD1F4 \uACBD\uACE0(\uC885\uD569)</a>\r\n\t\t\t\t<span class=\"medium\">\uB274\uC2A41</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n00508?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uC774\uB780\uD575\uD611\uC815 \uAE30\uB85C\u2026\uD2B8\uB7FC\uD504\uC758 \uB05D\uC5C6\uB294 '\uC624\uBC14\uB9C8 \uC9C0\uC6B0\uAE30'</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t</div><!-- //\uAD00\uB828\uAE30\uC0AC \uB9AC\uC2A4\uD2B8 -->\r\n</div>\r\n</div><!-- //Cluster : clusterType1 [\uAE30\uBCF8\uD615] -->\t\t\t\t\t\t\t\t");
		assertEquals(expected, news.get(0).getBody());
	}

	@Test
	public void decorate() throws Exception {
		String expected = "<h4 style=\"margin: 0px; padding: 0px 0px 15px; font-stretch: normal; font-size: 12pt; line-height: normal; font-family: Dotum, Helvetica, sans-serif; color: #2d589e; letter-spacing: -1px;\">'\u7F8E-\uC774\uB780 \uD575 \uD569\uC758' \uAC08\uB4F1</h4>\t<div style=\"color: #666666; font-family: Gulim, Dotum, Helvetica, sans-serif; font-size: 12px;\">\r\n\t\t<!-- \uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\t\t<div style=\"position: relative; overflow: hidden; margin: 0px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://news.nate.com/view/20171017n07710?mid=n0500\" style=\"text-decoration-line: none; padding: 0px 0px 3px; color: #777777 !important;\"  >\r\n\t\t\t\t<span style=\"position: relative; display: block; float: left; height: 80px; margin-right: 15px;\">\r\n\t\t\t\t\t<img style=\"border: 1px solid #e8e8e8; display: block;\" src=\"http://thumbnews.nateimg.co.kr/news90/http://news.nateimg.co.kr/orgImg/yt/2017/10/17/C0A8CA3D0000015F24859BF60019EC2D_P2.jpeg\"  alt=\"\" />\r\n\t\t\t\t</span>\t\t\t\t<span style=\"overflow: hidden; display: block; line-height: 18px; color: #666666;\">\r\n\t\t\t\t\t\t\t\t\t\t<strong style=\"display: block; padding-bottom: 4px; font-family: Dotum, Helvetica, sans-serif; font-size: 16px; color: #111111; letter-spacing: -1px; line-height: normal;\">\uD2B8\uB7FC\uD504 &quot;\uC774\uB780 \uD575\uD569\uC758 '\uC644\uC804\uD55C \uC885\uACB0' \uAC00\uB2A5\uC131 \uC788\uB2E4&quot;</strong>\r\n\t\t\t\t\t(\uC11C\uC6B8=\uC5F0\uD569\uB274\uC2A4) \uAD8C\uD61C\uC9C4 \uAE30\uC790 = \uB3C4\uB110\uB4DC \uD2B8\uB7FC\uD504 \uBBF8\uAD6D \uB300\uD1B5\uB839\uC774 \uC774\uB780 \uD575\uD569\uC758 \uBD88\uC778\uC99D\uC744 \uB118\uC5B4 '\uC644\uC804\uD55C \uC885\uACB0'(total termination) \uAC00\uB2A5\uC131\uC744 \uC2DC\uC0AC\uD588\uB2E4.     \uC678\uC2E0\uC5D0 \uB530\uB974\uBA74 \uD2B8\uB7FC\uD504 \uB300\uD1B5\uB839\uC740 ...\t\t\t\t</span>\r\n\t\t\t</a>\r\n\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">\uC5F0\uD569\uB274\uC2A4<em>2017-10-17</em></span>\r\n\t\t</div><!-- //\uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\r\n\t\t<!-- \uAD00\uB828\uAE30\uC0AC \uB9AC\uC2A4\uD2B8 -->\r\n\t\t<ul class=\"mduList1\">\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171015n02330?mid=n0500\"  >\uC774\uB780 \uD575\uD611\uC815 '\uBD88\uC778\uC99D' \uD6C4\uC18D\uC785\uBC95 \uB193\uACE0 \uACF5\uD654\uB2F9 \uB09C\uAE30\uB958</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">KBS\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n14430?mid=n0500\"  >\uD2B8\uB7FC\uD504 '\uC774\uB780 \uD575\uD611\uC815 \uBD88\uC778\uC99D'\u2026\uAD6D\uC81C\uC0AC\uD68C \uBC18\uC751 \uC5C7\uAC08\uB824</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">JTBC</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n09301?mid=n0500\"  >\u7F8E \uBE7C\uACE0 \uBAA8\uB450 \uC774\uB780\uD575\uD569\uC758 \uC9C0\uC9C0\u2026\uC2EC\uD310\uAE30\uAD6C\uB3C4 \"\uD2B8\uB7FC\uD504 \uD2C0\uB838\uB2E4\"(\uC885\uD569)</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n10282?mid=n0500\"  >\uD5E4\uC77C\uB9AC \uC720\uC5D4\uB300\uC0AC\uAC00 '\uC774\uB780\uD575\uD569\uC758 \uBB34\uB825\uD654' \uCD1D\uB300 \uBA68\uB2E4</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n03465?mid=n0500\"  >\uD2B8\uB7FC\uD504 \uBD88\uC778\uC99D \uC120\uC5B8\uC5D0 \uAD6D\uC81C\uC0AC\uD68C \uBC18\uBC1C\u2026IAEA \"\uC774\uB780, \uD575\uD611\uC815 \uC900\uC218\"</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n01578?mid=n0500\"  >\uD2B8\uB7FC\uD504, \uC774\uB780 \uD575\uD611\uC815 \uC778\uC99D \uAC70\uBD80\u2026\uC5B8\uC81C\uB4E0 \uD0C8\uD1F4 \uACBD\uACE0(\uC885\uD569)</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">\uB274\uC2A41</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li style=\"margin: 0px; padding: 0px 0px 5px 11px; list-style: none; line-height: 14px; background: url('http://static.news.naver.net/image/news/2009/ico_list_sub2.gif') 0px 2px no-repeat;\">\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n00508?mid=n0500\"  >\uC774\uB780\uD575\uD611\uC815 \uAE30\uB85C\u2026\uD2B8\uB7FC\uD504\uC758 \uB05D\uC5C6\uB294 '\uC624\uBC14\uB9C8 \uC9C0\uC6B0\uAE30'</a>\r\n\t\t\t\t<span style=\"font-stretch: normal; font-size: 11px; line-height: normal; font-family: Dotum, Helvetica, sans-serif; letter-spacing: -1px; display: inline-block; padding-top: 3px; color: #b89494 !important;\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t</div>";
		String actual = collector.decorateBody(
				"<h4>'\u7F8E-\uC774\uB780 \uD575 \uD569\uC758' \uAC08\uB4F1</h4>\t<div class=\"clusterType1\">\r\n\t\t<!-- \uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\t\t<div class=\"mlt01\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"http://news.nate.com/view/20171017n07710?mid=n0500\" class=\"lt1\" onclick=\"ndrclick('RMG01');\" >\r\n\t\t\t\t<span class=\"ib\">\r\n\t\t\t\t\t<img src=\"http://thumbnews.nateimg.co.kr/news90/http://news.nateimg.co.kr/orgImg/yt/2017/10/17/C0A8CA3D0000015F24859BF60019EC2D_P2.jpeg\" onerror=\"blankImg(this,0,0)\" alt=\"\" />\r\n\t\t\t\t</span>\t\t\t\t<span class=\"tb\">\r\n\t\t\t\t\t\t\t\t\t\t<strong class=\"tit\">\uD2B8\uB7FC\uD504 &quot;\uC774\uB780 \uD575\uD569\uC758 '\uC644\uC804\uD55C \uC885\uACB0' \uAC00\uB2A5\uC131 \uC788\uB2E4&quot;</strong>\r\n\t\t\t\t\t(\uC11C\uC6B8=\uC5F0\uD569\uB274\uC2A4) \uAD8C\uD61C\uC9C4 \uAE30\uC790 = \uB3C4\uB110\uB4DC \uD2B8\uB7FC\uD504 \uBBF8\uAD6D \uB300\uD1B5\uB839\uC774 \uC774\uB780 \uD575\uD569\uC758 \uBD88\uC778\uC99D\uC744 \uB118\uC5B4 '\uC644\uC804\uD55C \uC885\uACB0'(total termination) \uAC00\uB2A5\uC131\uC744 \uC2DC\uC0AC\uD588\uB2E4.     \uC678\uC2E0\uC5D0 \uB530\uB974\uBA74 \uD2B8\uB7FC\uD504 \uB300\uD1B5\uB839\uC740 ...\t\t\t\t</span>\r\n\t\t\t</a>\r\n\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4<em>2017-10-17</em></span>\r\n\t\t</div><!-- //\uC774\uBBF8\uC9C0 \uAE30\uC0AC -->\r\n\r\n\t\t<!-- \uAD00\uB828\uAE30\uC0AC \uB9AC\uC2A4\uD2B8 -->\r\n\t\t<ul class=\"mduList1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171015n02330?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uC774\uB780 \uD575\uD611\uC815 '\uBD88\uC778\uC99D' \uD6C4\uC18D\uC785\uBC95 \uB193\uACE0 \uACF5\uD654\uB2F9 \uB09C\uAE30\uB958</a>\r\n\t\t\t\t<span class=\"medium\">KBS\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n14430?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504 '\uC774\uB780 \uD575\uD611\uC815 \uBD88\uC778\uC99D'\u2026\uAD6D\uC81C\uC0AC\uD68C \uBC18\uC751 \uC5C7\uAC08\uB824</a>\r\n\t\t\t\t<span class=\"medium\">JTBC</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n09301?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\u7F8E \uBE7C\uACE0 \uBAA8\uB450 \uC774\uB780\uD575\uD569\uC758 \uC9C0\uC9C0\u2026\uC2EC\uD310\uAE30\uAD6C\uB3C4 \"\uD2B8\uB7FC\uD504 \uD2C0\uB838\uB2E4\"(\uC885\uD569)</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n10282?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD5E4\uC77C\uB9AC \uC720\uC5D4\uB300\uC0AC\uAC00 '\uC774\uB780\uD575\uD569\uC758 \uBB34\uB825\uD654' \uCD1D\uB300 \uBA68\uB2E4</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n03465?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504 \uBD88\uC778\uC99D \uC120\uC5B8\uC5D0 \uAD6D\uC81C\uC0AC\uD68C \uBC18\uBC1C\u2026IAEA \"\uC774\uB780, \uD575\uD611\uC815 \uC900\uC218\"</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n01578?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uD2B8\uB7FC\uD504, \uC774\uB780 \uD575\uD611\uC815 \uC778\uC99D \uAC70\uBD80\u2026\uC5B8\uC81C\uB4E0 \uD0C8\uD1F4 \uACBD\uACE0(\uC885\uD569)</a>\r\n\t\t\t\t<span class=\"medium\">\uB274\uC2A41</span>\r\n\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t<a href=\"http://news.nate.com/view/20171014n00508?mid=n0500\" onclick=\"ndrclick('RMG01');\" >\uC774\uB780\uD575\uD611\uC815 \uAE30\uB85C\u2026\uD2B8\uB7FC\uD504\uC758 \uB05D\uC5C6\uB294 '\uC624\uBC14\uB9C8 \uC9C0\uC6B0\uAE30'</a>\r\n\t\t\t\t<span class=\"medium\">\uC5F0\uD569\uB274\uC2A4</span>\r\n\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t</div>");
		assertEquals(expected, actual);
	}

	@Test
	public void execute() throws Exception {
		collector.execute(false);
	}
}
