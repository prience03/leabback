package com.duolebo.appbase.prj.bmtv.protocol;

/***
 * APK 布局代码
 * 
 * @author nzx 2014-7-2 09:59:58
 * 
 */
public enum LayoutCode {

	// ########推荐布局##############
	/** 推荐*/
	Rec_Table("Rec_Table"),
	/** 推荐--电影 */
	Rec_Movie("Rec_Movie"),
	/** 推荐--电视剧 */
	Rec_Series("Rec_Series"),
	/** 推荐--大片/家庭影院 */
	Rec_HomeTV("Rec_HomeTV"),
	/** 推荐--综艺 */
	Rec_Variety("Rec_Variety"),
	/** 推荐—少儿 */
	Rec_Children("Rec_Children"),
	/** 推荐-纪录片 */
	Rec_Record("Rec_Record"),
	/** 推荐-新闻 */
	Rec_News("Rec_News"),
	/** 推荐-娱乐 */
	Rec_Entertainment("Rec_Entertainment"),
	/** 推荐-音乐 */
	Rec_Music("Rec_Music"),
	/** 推荐-体育 */
	Rec_Sports("Rec_Sports"),
	/** 推荐-教育 */
	Rec_Education("Rec_Education"),
	/** 推荐-军事 */
	Rec_Military("Rec_Military"),
	/** 推荐-时尚 */
	Rec_Fashion("Rec_Fashion"),
	/** 推荐-生活 */
	Rec_Life("Rec_Life"),
	/** 推荐-排行 */
	Rec_TopList("Rec_TopList"),
	/** 推荐-最新 */
	Rec_Newest("Rec_Newest"),
	/** 推荐-视频 */
	Video("Video"),
	/** 推荐-资讯类列表 */
	Text_Head_Lines("Text_Head_Lines"),
	/** 推荐-轮播 */
	Scroll_Video("Scroll_Video"),
	// ########详细布局##############
	/** 详细页-电视剧 */
	Detail_Series("Detail_Series"),
	/** 详细页-电影 */
	Detail_Movie("Detail_Movie"),
	/** 详细页-资讯类（非影视剧） */
	Detail_News("Detail_News"),
	/** 详细页-大片/家庭影院 */
	Detail_HomeTV("Detail_HomeTV"),
	/** 拆条详细页 */
	Detail_Chaitiao("Detail_Chaitiao"),
	/** 详细页-综艺*/
	Detail_Variety("Detail_Variety"),
	/** 详细页-专辑*/
	Detail_Album("Detail_Album"),
	/** 详细页-专题*/
	Detail_Subject("Detail_Subject"),
	// ########片库布局##############
	/** 片库-电影 */
	VideoLibrary_Movie("VideoLibrary_Movie"),
	/** 片库-电视剧 */
	VideoLibrary_Series("VideoLibrary_Series"),
	/** 片库-资讯类 */
	VideoLibrary_News("VideoLibrary_News"),
	/** 片库-电视节目 */
	VideoLibrary_Column("VideoLibrary_Column"),
	/** 片库-音乐 */
	VideoLibrary_Music("VideoLibrary_Music"),
	/** 片库-综艺*/
	VideoLibrary_Variety("VideoLibrary_Variety"),
	// ########专题布局##############
	
	/** 专辑*/
	Music_AlbumList("Music_AlbumList"),
	
	/** 专题*/
	SubjectList("SubjectList"),
	/** 专题-网页 */
	Link("Link"),
	/** 专题列表-影视剧 */
	MOVIE_SUBJECT_TP1("MOVIE_SUBJECT_TP1"),
	/** 专题列表-非影视剧 */
	NEWS_SUBJECT_TP1("NEWS_SUBJECT_TP1"),
	// ########分类布局##############
	/** 分类-电影、电视剧、短视频 */
	Category("Category"),
	/** 分类-电视节目 */
	Category_Column("Category_Column"),
	/** 歌手专辑 */
	Category_Singer("Category_Singer"),
	// ########筛选布局##############
	/** 筛选-电影 */
	Classifications_Movie("Classifications_Movie"),

	// ########搜索、历史、收藏布局##############
	/** 搜索 搜索结果页 */
	Search("Search"),
	/** 我的历史记录 */
	History("History"),
	/** 我的收藏 */
	Favorite("Favorite"),
	// ########其他布局##############
	/** 点播节目布局 */
	DianBo("DianBo"),
	/** 个人布局 */
	Personal("Personal"),
	/** 设置 */
	Setting("Setting"),
	/** 营业厅 */
	Business_Hall("Business_Hall"),
	/** 消息 */
	Message("Message"),
	/** 全屏播放 */
	FullScreen("FullScreen"),
	/** 音乐专辑列表 */
	Category_Music("Category_Music"),
	/** 最新 */
	VideoLibrary_Newest("VideoLibrary_Newest"),
	// ########还未用到的布局##############
	/** 军事-专家评论 */
	Military_Comment("Military_Comment"),
	/** 体育-赛事大厅 */
	Sports_Hall("Sports_Hall"),
	/** 新闻-名嘴观点 */
	News_Comment("News_Comment"),
	/** 央视卫视王牌节目 */
	TV_FamousPrograms("TV_FamousPrograms"),
	
	/** 推荐--大片/家庭影院，4k、3d、杜比*/
	HomeTV("HomeTV"),
	
	//Start_Apk
	Start_Apk("Start_Apk"),
	
	/** apk 自定义 */
	Search_Classify("Search_Classify"),
	History_Classify("History_Classify"),
	History_Classify_Item("History_Classify_Item"),
	Message_Item("Message_Item"),
	RechargeCard_Activity("RechargeCard_Activity"),
	
	Variety_List("Variety_List"),
	Singer_Album_List("Singer_Album_List"),

	WeChat_TV("WeChat_TV"),

	Personal_Category("Personal_Category"),
	Rec_Table_Category("Rec_Table_Category"),
	VideoLibrary_Movie_Category("VideoLibrary_Movie_Category"),

	UNKNOWN_LAYOUT("UNKNOWN_LAYOUT");

	private String layoutStr;

	LayoutCode(String code) {
		this.layoutStr = code;
	}

	public String toString() {
		return this.layoutStr;
	}

	public static LayoutCode fromString(String layoutStr) {
	    for (LayoutCode lc : LayoutCode.values()) {
	        if (lc.layoutStr.equalsIgnoreCase(layoutStr)) {
	            return lc;
	        }
	    }
	    return UNKNOWN_LAYOUT;
	}

}