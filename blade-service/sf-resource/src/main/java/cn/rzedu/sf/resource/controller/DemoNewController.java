package cn.rzedu.sf.resource.controller;//package cn.rzedu.sf.resource.controller;

import cn.rzedu.sf.resource.entity.*;
import cn.rzedu.sf.resource.entity.Character;
import cn.rzedu.sf.resource.service.*;
import cn.rzedu.sf.resource.util.ExcelUtil;
import cn.rzedu.sf.resource.util.HtmlUtil;
import cn.rzedu.sf.resource.util.TextTransAudio;
import cn.rzedu.sf.resource.vo.TextbookLessonVO;
import cn.rzedu.sf.resource.vo.TextbookVO;
import cn.rzedu.sf.resource.vo.VideoVO;
import cn.rzedu.sf.resource.vo.WordVO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springblade.core.tool.utils.FileUtil;
import org.springblade.resource.entity.EntityFile;
import org.springblade.resource.feign.EntityFileClient;
import org.springblade.resource.service.impl.EntityFileServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/demo-new")
public class DemoNewController {

	ICharacterService characterService;
	ICharacterResourceService characterResourceService;
	ICharacterResourceFileService characterResourceFileService;
	EntityFileClient entityFileClient;
	ICourseLessonService courseLessonService;
	ICourseResourceService courseResourceService;
	IWordService wordService;
	IVideoService videoService;
	ITextbookService textbookService;
	ITextbookLessonService textbookLessonService;
	ITextbookLessonCharacterService textbookLessonCharacterService;

	@SneakyThrows
	@PostMapping("/test/importFile")
	public Object putFile1(@RequestParam(value = "file", required = false) MultipartFile file,
						   @RequestParam(value = "text", required = false) String text,
						   @RequestParam(value = "characterName", required = false) String characterName,
						   @RequestParam(value = "font", required = false) String font,
						   @RequestParam(value = "resourceType", required = false) Integer resourceType,
						   @RequestParam(value = "objectId", required = false) String objectId) {
		if (file == null && text != null) {
			boolean flag = characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectId, text);
			return flag;
		} else {
			File file1 = null;
			if (file.equals("") || file.getSize() <= 0) {
				file = null;
			} else {
				InputStream ins = null;
				ins = file.getInputStream();
				file1 = new File(file.getOriginalFilename());
				inputStreamToFile(ins, file1);
				ins.close();
			}
			EntityFile entityFile = uploadFile(file1, objectId);
			if (entityFile != null) {
				String uuid = entityFile.getUuid();
				characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectId, uuid);
			}
			System.gc();//在删除之前调用垃圾回收,这样jvm就不会占用文件了
			try {
				Thread.sleep(100);//我这边是这样,如果不休眠一会,依旧无法删除
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			delteTempFile(file1);
			return entityFile;
		}
	}

	/**
	 * 批量修改上传文件
	 *
	 * @return ObjectStat
	 */
	@ApiLog("demo-new")
	@SneakyThrows
	@GetMapping("/test")
//	@ApiOperation(value = "上传文件", notes = "oss上传文件接口" , position = 1)
	public Object putFile() {
		String code = "7931155"; //出版社code
		String font = "欧体"; //字体

		//1.软笔资源导入
//        File f = new File("F:\\SpringBladeToB\\resource\\test");   //文件源地址
//        File f = new File("E:\\shufa-resource\\湖南美术出版社");   //文件源地址
//		uuids = "";
//		names = "";
//		importResource(f, font); //教材 不同年级 字体可能不一样

		//随堂资源补充
//		practiceResource("北师大版", font);

		//2.导入软笔文本语音
//		importTxt(code, font);

		//3.导入语音
//		File f = new File("C:\\Users\\80969\\Desktop\\temp\\2");   //文件源地址
//		importAudio(f, font, true);

//		4.导入软笔 书写要点文本
//		importTxt2(code, font);

		//5.读取excel文件 导入空间笔划特征
//		File f = new File("F:\\SpringBladeToB\\resource\\fenxi.xls");
//		importFeatures(f, font, code);

		//6.导入语音
//		File f = new File("C:\\Users\\80969\\Desktop\\temp\\4");   //文件源地址
//		importAudio(f, font, false);

		//7.给笔画 添加 图片进 light_image
//		addImageForWord();

//		String publisher = "陕西科技";
//		//8.导入软笔目录
//		importRBDir(code, font, publisher);


		//end:统计软笔 缺少资源
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("三年级上册", "现代简楷");
//		map.put("三年级下册", "现代简楷");
//		map.put("四年级上册", "欧体");
//		map.put("四年级下册", "欧体");
//		map.put("五年级上册", "欧体");
//		map.put("五年级下册", "欧体");
//		map.put("六年级上册", "赵体");
//		map.put("六年级下册", "赵体");
//		tongji(publisher, map);


		//统计硬笔 缺少资源
//		tongjiYB();

		//导入硬笔目录
//		importYB();


		//导入一起来找茬
//		File f = new File("F:\\SpringBladeToB\\resource\\zhaocha");
//		chars = "";
//		falseList = new ArrayList<>();
//		trueList = new ArrayList<>();
//		importZC(f);

//		importZCT();

		//导入硬笔书写要点
//		importYDText();

		//修改文本
//		textModify();

		//修改软笔目录页
//		modifySoftTextBool(publisher);

//		modifyTEXT();

		//导入初中教学资源
		File f = new File("E:\\shufa-resource\\陕西科技初中版");   //文件源地址
		System.out.println("11111111111111111111");
		importMiddleResource(f, font);
		return null;
	}

	private void importMiddleResource(File f1, String font) throws IOException {
		if (f1.isDirectory()) {
			File[] f2 = f1.listFiles();
			for (int i = 0; i < f2.length; i++) {
				File file = f2[i];
				if (file.isFile()) {
					String fileExtension = FileUtil.getFileExtension(file.getName());
					if ("png".equals(fileExtension) || "jpg".equals(fileExtension) || "gif".equals(fileExtension)
							|| "flv".equals(fileExtension) || "mp4".equals(fileExtension) || "mp3".equals(fileExtension) || "txt".equals(fileExtension)) {
						String path = file.getPath();
						String resultDirectory = path.replaceAll("\\\\", "/");
						String arr[] = resultDirectory.split("/");
						String name = arr[arr.length - 1]; //当前文件名
						String parentName = arr[arr.length - 2]; //上级文件名
						String characterName = arr[arr.length - 3]; //上上级文件名
						String gradeName = arr[arr.length - 4]; //年级文件名

						boolean flag = true;
						if(gradeName.contains("七年级上册")||gradeName.contains("七年级下册")){
							font = "欧体";
							flag = true;
						} else {
							font = "字体1";
							flag = false;
						}

						if(flag){ //七年级

						} else {

						}

						if(parentName.equals("笔法")){
							if(name.equals("2.gif")){
								name = "行笔路线.gif";
							} else if(name.equals("3.gif")){
								name = "书写动画.gif";
							}
						} else if(parentName.equals("对比")){

						} else if(parentName.equals("分析")){
							if(name.equals("1.mp3")){
								name = "笔画特征.mp3";
							} else if(name.equals("1.txt")){
								name = "笔画特征文本.txt";
							} else if(name.equals("2.mp3")){
								name = "空间特征.mp3";
							} else if(name.equals("2.txt")){
								name = "空间特征文本.txt";
							}
						} else if(parentName.equals("观察")){
							if(name.equals("1.jpg")){
								name = "字形结构.png";
							} else if(name.equals("2.jpg")){
								name = "字形特征1";
							} else if(name.equals("3.jpg")){
								name = "字形特征2";
							} else if(name.equals("4.jpg")){
								name = "字形特征3";
							}
						} else if(parentName.equals("认读")){
							if(name.equals("1.jpg")){
								name = "米底空心字.png";
							} else if(name.equals("1.mp3")){
								name = "认读音频";
							} else if(name.equals("2.jpg")){
								name = "六书演变.png";
							}
						} else if(parentName.equals("视频")){

						} else if(parentName.equals("欣赏")){
							if(name.equals("1.jpg")){
								name = "碑帖欣赏.png";
							} else if(name.equals("2.jpg")){
								name = "碑帖缩落.png";
							}
						}

						String objectCode = judgeResource(name);
						if (objectCode == null) {
//							saveAsFileWriter(name + "***************************************************************" + parentName);
							continue;
						}
						Integer resourceType = getResourceTypeByCode(objectCode);

//						saveAsFileWriter(gradeName + " | " + characterName + " | " + parentName + " | " + font + " | " + name + " | " + objectCode + " | " + resourceType);
						if(name.contains("txt")){
							FileInputStream f =new FileInputStream(file.getPath());
							InputStreamReader reader = new InputStreamReader( f ,"GB2312"); //在此转码即可
							BufferedReader bufferedReader = new BufferedReader(reader); //构造一个BufferedReader类来读取文件
							String content = "";
							String strTmp = "";//数据集
							while((strTmp = bufferedReader.readLine())!=null){
								content += strTmp;// 打印结果
							};
							saveAsFileWriter(content);
							if(!"".equals(content)){
								characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, content);
							}
						} else {
							EntityFile entityFile = uploadFile(file, objectCode);
							if (entityFile != null) {
								String uuid = entityFile.getUuid();
								if ("compare_image".equals(objectCode)) {
									String name_new = name.replaceAll(".jpg", "").replaceAll(".png", "");
									if ("".equals(uuids)) {
										names = name_new;
										uuids = uuid;
									} else {
										names = names + "," + name_new;
										uuids = uuids + "," + uuid;
									}

									if (i == (f2.length/2 - 1)) {
										characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, uuids);
										objectCode = "compare_text";
										resourceType = getResourceTypeByCode(objectCode);
										characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, names);
										names = "";
										uuids = "";
									}
//								} else if ("practice_images".equals(objectCode)) {
//									if ("".equals(uuids)) {
//										uuids = uuid;
//									} else {
//										uuids = uuids + "," + uuid;
//									}
//
//									if (i == (f2.length - 1)) {
//										characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, uuids);
//										uuids = "";
//									}
								} else {
									characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, uuid);
								}
							}
						}
					}
				}
				if (f1.isDirectory()) {
					importMiddleResource(file, font); //递归调用
				}
			}
		}
	}

	private void modifySoftTextBool(String publisher) {
		Textbook textbook = new Textbook();
		textbook.setPublisher(publisher);
		List<Textbook> list = textbookService.list(Wrappers.query(textbook));
		for (Textbook textbook1 : list) {
			Integer id = textbook1.getId();
			List<TextbookLessonVO> lessonByTextbookList = textbookLessonService.findLessonByTextbookId(id);
			for (TextbookLessonVO textbookLessonVO : lessonByTextbookList) {
				String name = textbookLessonVO.getName();
				String chars = textbookLessonVO.getChars();

				String[] split = name.split("、");
				String lessionName = split[0];
				String str = split[1];

				str = str.replaceAll(" ", "&nbsp;&nbsp;");

				String result = lessionName + "、" + str;

				System.out.println(name);
				System.out.println(chars);
				System.out.println(result);

				TextbookLesson textbookLesson = new TextbookLesson();
				textbookLesson.setId(textbookLessonVO.getId());
				textbookLesson.setName(result);
				textbookLessonService.updateById(textbookLesson);
			}
		}
	}


	private void textModify() {
		CharacterResourceFile characterResourceFile = new CharacterResourceFile();
		characterResourceFile.setObjectId("paraphrase_text");
		characterResourceFile.setIsDeleted(0);
		List<CharacterResourceFile> list = characterResourceFileService.list(Wrappers.query(characterResourceFile));
		for (CharacterResourceFile resourceFile : list) {
			String content = resourceFile.getContent();
//			if(content.contains(",")){
//				System.out.println(content);
//				content = content.replaceAll(",", "，");
//				System.out.println("修改后：" + content);
//				resourceFile.setContent(content);
//				characterResourceFileService.updateById(resourceFile);
//			}
			int index = content.indexOf("成语");
			if (index != -1) {
				String substring = content.substring(0, index);
				System.out.println(substring);
				int i = substring.indexOf(",");
				if (i == -1) {
					substring = substring.replaceAll("。。。", "");
					substring = substring + "。" + content.substring(index);
//					saveAsFileWriter(substring);
					resourceFile.setContent(substring);
					characterResourceFileService.updateById(resourceFile);
				} else {

				}
			}

		}
	}

	private void importYDText() {
		Iterator<Map.Entry<Integer, String>> it = gradeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			Integer gradeId = entry.getKey();
			String gradeName = entry.getValue();
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());

			Iterator<Map.Entry<Integer, String>> itlesson = lessonMap.entrySet().iterator();
			while (itlesson.hasNext()) {
				Map.Entry<Integer, String> lessonEntry = itlesson.next();
				Integer lessonId = lessonEntry.getKey();
				String lessonName = lessonEntry.getValue();

				Word w = new Word();
				w.setCnsversion("5809328"); //人民硬笔
				w.setCnnjcode(gradeId + ""); // 年级code
				w.setCnsclasshour(lessonId + ""); //课时
				List<Word> wordList = wordService.list(Wrappers.query(w));
				if (wordList != null && wordList.size() > 0) {
					for (Word word : wordList) {
						List<WordVO> wordVOList = wordService.selectWordVoById(Integer.valueOf(word.getCnid().toString()));
						if (wordVOList != null && wordVOList.size() > 0) {
							WordVO wordVO = wordVOList.get(0);
							String characterName = wordVO.getCnsfontname();
							String content = wordVO.getContent();
							if (content != null && !"".equals(content)) {
								String textFromHtml = HtmlUtil.getTextFromHtml(content);
								textFromHtml = textFromHtml.replaceAll("书写要点：", "");
								textFromHtml = textFromHtml.replaceAll("书写要点:", "");
								textFromHtml = textFromHtml.replaceAll("书写要领：", "");
								textFromHtml = textFromHtml.replaceAll("书写要宽：", "宽");
								System.out.println("过滤标签后： ");
								System.out.println(textFromHtml);
								if (!"".equals(textFromHtml) && !"".equals(textFromHtml.trim())) {
//字符串长度
									int strlenth = textFromHtml.length();
									int blankcount = 0;
									if (strlenth <= 4) {
										blankcount = 0;
									} else {
										blankcount = strlenth % 4 > 0 ? strlenth / 4 : textFromHtml.length() / 4 - 1; //需要加空格数量
									}
									if (blankcount > 0) {
										for (int i = 0; i < blankcount; i++) {
											textFromHtml = textFromHtml.substring(0, (i + 1) * 4 + i) + "，" + textFromHtml.substring((i + 1) * 4 + i, strlenth + i);
										}
									}
									System.out.println(textFromHtml);
									characterResourceService.createHardResourceFile(characterName, 722, "chalk_text", textFromHtml);
									characterResourceService.createHardResourceFile(characterName, 723, "pen_text", textFromHtml);
									characterResourceService.createHardResourceFile(characterName, 724, "learn_text", textFromHtml);
								}

							}
						}
					}
				}
			}
		}

	}

	//
	private void importZCT() {
		/* 读取数据 */
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\80969\\Desktop\\test.txt")),
					"UTF-8"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				System.out.println(lineTxt);
				String[] split = lineTxt.split("\\|");
				for (int i = 0; i < split.length; i++) {
					String chatacterName = split[0];
					String status = split[1];
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println("read errors :" + e);
		}

	}

	private static String chars = "";
	private static List<String> falseList = new ArrayList<>();
	private static List<String> trueList = new ArrayList<>();

	private void importZC(File f1) throws IOException {
		if (f1.isDirectory()) {
			File[] f2 = f1.listFiles();
			for (int i = 0; i < f2.length; i++) {
				File file = f2[i];
				if (file.isFile()) {
					String fileExtension = FileUtil.getFileExtension(file.getName());
					if ("png".equals(fileExtension) || "jpg".equals(fileExtension)) {
						String path = file.getPath();
						String resultDirectory = path.replaceAll("\\\\", "/");
						String arr[] = resultDirectory.split("/");
						String name = arr[arr.length - 1]; //当前文件名
						String characterName = arr[arr.length - 3]; //上上级文件名

						if ("".equals(chars)) {
							chars = characterName;
							if (name.contains("false")) {
								falseList.add(file.getPath());
							} else {
								trueList.add(file.getPath());
							}
						} else if (chars.equals(characterName)) {
							if (name.contains("false")) {
								falseList.add(file.getPath());
							} else {
								trueList.add(file.getPath());
							}

							if (trueList.size() == 1 && falseList.size() == 3) {
								String err = "";
								String t = "";
								if (falseList != null && falseList.size() > 0) {
									for (String s : falseList) {
										err += s + "|";
									}
								}

								if (trueList != null && trueList.size() > 0) {
									for (String s : trueList) {
										t += s + "|";
									}
								}
								System.out.println(err);
								System.out.println(t);
//								saveAsFileWriter(chars + "|true|" + t);
//								saveAsFileWriter(chars + "|false|" + err);

								String value = "";
								if (trueList.size() == 1 && falseList.size() == 3) {
									File tFile = new File(trueList.get(0));
									EntityFile entityFile = entityFileClient.uploadImage(tFile);

									File fFile1 = new File(falseList.get(0));
									File fFile2 = new File(falseList.get(1));
									File fFile3 = new File(falseList.get(2));

									EntityFile entityFileF1 = entityFileClient.uploadImage(fFile1);
									EntityFile entityFileF2 = entityFileClient.uploadImage(fFile2);
									EntityFile entityFileF3 = entityFileClient.uploadImage(fFile3);

									if (entityFile != null && entityFileF1 != null && entityFileF2 != null && entityFileF3 != null) {
										String uuid = entityFile.getUuid();

										String uuid1 = entityFileF1.getUuid();
										String uuid2 = entityFileF2.getUuid();
										String uuid3 = entityFileF3.getUuid();
										value = uuid + "," + uuid1 + "," + uuid2 + "," + uuid3;
										System.out.println(value);
										characterResourceService.createHardResourceFile(chars, 725, "game", value);
									}
								}
							}
						} else {
//							String value = "";
//							if(trueList.size() == 1 && falseList.size() == 3){
//								File tFile = new File(trueList.get(0));
//								EntityFile entityFile = entityFileClient.uploadImage(tFile);
//
//								File fFile1 = new File(falseList.get(0));
//								File fFile2 = new File(falseList.get(1));
//								File fFile3 = new File(falseList.get(2));
//
//								EntityFile entityFileF1 = entityFileClient.uploadImage(fFile1);
//								EntityFile entityFileF2 = entityFileClient.uploadImage(fFile2);
//								EntityFile entityFileF3 = entityFileClient.uploadImage(fFile3);
//
//								if(entityFile!=null && entityFileF1 != null && entityFileF2 != null && entityFileF3 != null){
//									String uuid = entityFile.getUuid();
//
//									String uuid1 = entityFileF1.getUuid();
//									String uuid2 = entityFileF2.getUuid();
//									String uuid3 = entityFileF3.getUuid();
//									value = uuid + "," + uuid1 + "," + uuid2 + "," + uuid3;
//									System.out.println(value);
//									characterResourceService.createHardResourceFile(chars, 725, "game", value);
//								}
//							}

							chars = characterName;
							trueList = new ArrayList<String>();
							falseList = new ArrayList<String>();

							if (name.contains("false")) {
								falseList.add(file.getPath());
							} else {
								trueList.add(file.getPath());
							}
						}

					}
				}
				if (f1.isDirectory()) {
					importZC(file); //递归调用
				}
			}
		}
	}

	//导入软笔目录
	private void importRBDir(String code, String font, String publisher) {
		Iterator<Map.Entry<Integer, String>> it = gradeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			Integer gradeId = entry.getKey();
			String gradeName = entry.getValue();
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
//			if ("7073".equals(gradeId + "") || "7074".equals(gradeId + "")) {
//				font = "赵体";
//			} else if ("1109".equals(gradeId + "") || "7068".equals(gradeId + "")) {
//				font = "现代简楷";
//			} else {
				font = "欧体";
//			}

			List<TextbookLessonVO> list = new ArrayList<TextbookLessonVO>();

			Iterator<Map.Entry<Integer, String>> itlesson = lessonMap.entrySet().iterator();
			while (itlesson.hasNext()) {
				Map.Entry<Integer, String> lessonEntry = itlesson.next();
				Integer lessonId = lessonEntry.getKey();
				String lessonName = lessonEntry.getValue();

				TextbookLessonVO textbookLessonVO = new TextbookLessonVO();
				String danyuan = ""; //单元名
				String lessonCharacter = "";
				String lessonCharacterName = "";
				Integer listOrder = judgeListOrder(lessonName);
				textbookLessonVO.setListOrder(listOrder);
				textbookLessonVO.setEnabled(true);


				Word w = new Word();
				w.setCnsversion(code); //出版社code
				w.setCnnjcode(gradeId + ""); // 年级code
				w.setCnsclasshour(lessonId + ""); //课时
				List<Word> wordList = wordService.list(Wrappers.query(w));
				if (wordList != null && wordList.size() > 0) {
					int i = 0;
					for (Word word : wordList) {
						danyuan = word.getCnsevolution();
						if (i == 0) {
							lessonCharacterName += word.getCnsfontname();
						} else {
							lessonCharacterName += "&nbsp;&nbsp;" + word.getCnsfontname();
						}
						lessonCharacter = lessonCharacter + "、" + word.getCnsfontname();
//						saveAsFileWriter(gradeName + " " + lessonName + " " + word.getCnsfontname());
						String characterName = word.getCnsfontname();
//						String pinyin = word.getCnspinyinzm();
//						String cnssimplified = word.getCnssimplified();
//						String cnsradical = word.getCnsradical();
//						String cnnstroke = word.getCnnstroke();

//						List<WordVO> wordVOList = wordService.selectWordVoById(Integer.valueOf(word.getCnid().toString()));

						//目录
//						System.out.println(gradeName + " : " + lessonName + " : " + characterName);
//						saveAsFileWriter(gradeName + " : " + lessonName + " : " + characterName);


						//插入硬笔认读
//						if(pinyin != null && !"".equals(pinyin)){
//							//拼音
//							characterResourceService.createHardResourceFile(characterName, 721, "spell", pinyin);
//						}
//						if(cnssimplified != null && !"".equals(cnssimplified)){
//							//简体
//							characterResourceService.createHardResourceFile(characterName, 721, "simple", cnssimplified);
//						}
//						if(cnsradical != null && !"".equals(cnsradical)){
//							//部首
//							characterResourceService.createHardResourceFile(characterName, 721, "radical", cnsradical);
//						}
//						if(cnnstroke != null && !"".equals(cnnstroke)){
//							//笔画
//							characterResourceService.createHardResourceFile(characterName, 721, "stroke_number", cnnstroke);
//						}

						//查询出汉字关联释义
						//用法 -- 过滤html标签
//						if(wordVOList != null && wordVOList.size() > 0){
//							WordVO wordVO = wordVOList.get(0);
//							String content = wordVO.getContent();
//							if(content != null && !"".equals(content)){
//								String textFromHtml = HtmlUtil.getTextFromHtml(content);
//								System.out.println("过滤标签后： ");
//								System.out.println(textFromHtml);
//								characterResourceService.createHardResourceFile(characterName, 721, "paraphrase_text", textFromHtml);
//								saveAsFileWriter(textFromHtml);
//								try {
//									Thread.sleep(1000);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
						//文本转语音
//								TextTransAudio.trans(textFromHtml, characterName, "C:\\Users\\80969\\Desktop\\temp");
//							}
//						}
						i++;
					}

					textbookLessonVO.setSectionName(danyuan);
					Integer section = judgeSection(danyuan);
					textbookLessonVO.setSection(section);
					textbookLessonVO.setName(lessonName + "、" + lessonCharacterName);
					lessonCharacter = lessonCharacter.substring(1, lessonCharacter.length());
					textbookLessonVO.setChars(lessonCharacter);

					list.add(textbookLessonVO);
				}
			}

			TextbookVO textbookVO = new TextbookVO();
			textbookVO.setTextbookLessonVOList(list);

			String gradeCode = judgeGradeCode(gradeName);
			String volume = judgeVolume(gradeName);
			if (gradeCode != null && volume != null) {
				textbookVO.setGradeCode(gradeCode);
				textbookVO.setVolume(volume);
				textbookVO.setSubject(71);
				textbookVO.setStageCode("3");
				textbookVO.setPublisher(publisher);
				textbookVO.setName(gradeName);
				textbookVO.setFont(font);

//				textbookVO.setTextbookLessonVOList();

//				save(textbookVO);
			}
			System.out.println(JSON.toJSON(textbookVO));
			saveAsFileWriter(JSON.toJSON(textbookVO) + "");
			saveAsFileWriter("");
		}

	}

	//导入硬笔目录
	private void importYB() {
		Iterator<Map.Entry<Integer, String>> it = gradeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			Integer gradeId = entry.getKey();
			String gradeName = entry.getValue();
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());

			List<TextbookLessonVO> list = new ArrayList<TextbookLessonVO>();

			Iterator<Map.Entry<Integer, String>> itlesson = lessonMap.entrySet().iterator();
			while (itlesson.hasNext()) {
				Map.Entry<Integer, String> lessonEntry = itlesson.next();
				Integer lessonId = lessonEntry.getKey();
				String lessonName = lessonEntry.getValue();

				TextbookLessonVO textbookLessonVO = new TextbookLessonVO();
				String danyuan = ""; //单元名
				String lessonCharacter = "";
				String lessonCharacterName = "";
				Integer listOrder = judgeListOrder(lessonName);
				textbookLessonVO.setListOrder(listOrder);
				textbookLessonVO.setEnabled(true);


				Word w = new Word();
				w.setCnsversion("7092"); //人民硬笔
				w.setCnnjcode(gradeId + ""); // 年级code
				w.setCnsclasshour(lessonId + ""); //课时
				List<Word> wordList = wordService.list(Wrappers.query(w));
				if (wordList != null && wordList.size() > 0) {
					for (Word word : wordList) {
						danyuan = word.getCnsevolution();
						lessonCharacterName += word.getCnsfontname();
						lessonCharacter = lessonCharacter + "、" + word.getCnsfontname();
//						saveAsFileWriter(gradeName + " " + lessonName + " " + word.getCnsfontname());
						String characterName = word.getCnsfontname();
//						String pinyin = word.getCnspinyinzm();
//						String cnssimplified = word.getCnssimplified();
//						String cnsradical = word.getCnsradical();
//						String cnnstroke = word.getCnnstroke();

//						List<WordVO> wordVOList = wordService.selectWordVoById(Integer.valueOf(word.getCnid().toString()));

						//目录
//						System.out.println(gradeName + " : " + lessonName + " : " + characterName);
//						saveAsFileWriter(gradeName + " : " + lessonName + " : " + characterName);


						//插入硬笔认读
//						if(pinyin != null && !"".equals(pinyin)){
//							//拼音
//							characterResourceService.createHardResourceFile(characterName, 721, "spell", pinyin);
//						}
//						if(cnssimplified != null && !"".equals(cnssimplified)){
//							//简体
//							characterResourceService.createHardResourceFile(characterName, 721, "simple", cnssimplified);
//						}
//						if(cnsradical != null && !"".equals(cnsradical)){
//							//部首
//							characterResourceService.createHardResourceFile(characterName, 721, "radical", cnsradical);
//						}
//						if(cnnstroke != null && !"".equals(cnnstroke)){
//							//笔画
//							characterResourceService.createHardResourceFile(characterName, 721, "stroke_number", cnnstroke);
//						}

						//查询出汉字关联释义
						//用法 -- 过滤html标签
//						if(wordVOList != null && wordVOList.size() > 0){
//							WordVO wordVO = wordVOList.get(0);
//							String content = wordVO.getContent();
//							if(content != null && !"".equals(content)){
//								String textFromHtml = HtmlUtil.getTextFromHtml(content);
//								System.out.println("过滤标签后： ");
//								System.out.println(textFromHtml);
//								characterResourceService.createHardResourceFile(characterName, 721, "paraphrase_text", textFromHtml);
//								saveAsFileWriter(textFromHtml);
//								try {
//									Thread.sleep(1000);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
						//文本转语音
//								TextTransAudio.trans(textFromHtml, characterName, "C:\\Users\\80969\\Desktop\\temp");
//							}
//						}
					}

					textbookLessonVO.setSectionName(danyuan);
					Integer section = judgeSection(danyuan);
					textbookLessonVO.setSection(section);
					textbookLessonVO.setName(lessonName + "、" + lessonCharacterName);
					lessonCharacter = lessonCharacter.substring(1, lessonCharacter.length());
					textbookLessonVO.setChars(lessonCharacter);

					list.add(textbookLessonVO);
				}
			}

			TextbookVO textbookVO = new TextbookVO();
			textbookVO.setTextbookLessonVOList(list);

			String gradeCode = judgeGradeCode(gradeName);
			String volume = judgeVolume(gradeName);
			if (gradeCode != null && volume != null) {
				textbookVO.setGradeCode(gradeCode);
				textbookVO.setVolume(volume);
				textbookVO.setSubject(71);
				textbookVO.setStageCode("2");
				textbookVO.setPublisher("华文出版");
				textbookVO.setName(gradeName);

//				textbookVO.setTextbookLessonVOList();

//				save(textbookVO);
			}
			System.out.println(JSON.toJSON(textbookVO));
			saveAsFileWriter(JSON.toJSON(textbookVO) + "");
			saveAsFileWriter("");
		}

	}

	private Boolean save(TextbookVO textbookVO) {
		//判断教材名是否重复
		Integer subject = textbookVO.getSubject();
		boolean isExist = textbookService.judgeRepeatByName(textbookVO.getName(), textbookVO.getPublisher(),
				subject, null);
		if (isExist) {
			return false;
		}
		//新增教材
		LocalDateTime now = LocalDateTime.now();
		textbookVO.setCreateDate(now);
		textbookVO.setModifyDate(now);
		boolean statusT = textbookService.save(textbookVO);
		if (!statusT) {
			return false;
		}
		//新增课程
		Integer textbookId = textbookVO.getId();
		boolean statusTL = false;
		Integer lessonId = null;
		//变动的课程id，用于修改课程汉字数
		List<Integer> lessonIdList = new ArrayList<>();
		List<TextbookLessonVO> lessonVOList = textbookVO.getTextbookLessonVOList();
		if (lessonVOList != null && !lessonVOList.isEmpty()) {
			for (TextbookLessonVO lessonVO : lessonVOList) {
				lessonVO.setTextbookId(textbookId);
				lessonVO.setCreateDate(now);
				lessonVO.setModifyDate(now);
				statusTL = textbookLessonService.save(lessonVO);
				if (!statusTL) {
					continue;
				}

				//课程-汉字建表时，判断汉字是否存在，不存在的不予保存，跳过
				lessonId = lessonVO.getId();
				lessonIdList.add(lessonId);
				String chars = lessonVO.getChars();
				addTextLessonCharacters(subject, lessonId, chars);
			}
		}
		//课程、汉字都添加完后，修改课程总数和汉字总数
		lessonIdList.forEach(LId -> textbookLessonService.updateCharCount(LId));
		textbookService.updateLessonAndCharCount(textbookId);

		return true;
	}

	private void addTextLessonCharacters(Integer subject, Integer lessonId, String chars) {
		if (StringUtils.isBlank(chars)) {
			return;
		}
		LocalDateTime now = LocalDateTime.now();
		List<Character> characterList = characterService.findByChars(chars);
		if (characterList != null && !characterList.isEmpty()) {
			List<TextbookLessonCharacter> tlcList = new ArrayList<>();
			TextbookLessonCharacter tlc = null;
			for (Character character : characterList) {
				tlc = new TextbookLessonCharacter();
				tlc.setLessonId(lessonId);
				tlc.setCharacterId(character.getId());
				tlc.setSubject(subject);
				tlc.setCreateDate(now);
				tlc.setModifyDate(now);
				tlcList.add(tlc);
			}
			textbookLessonCharacterService.saveBatch(tlcList);
		}
	}

	private static String[] codeArr = {
			"spell",
			"white_character",
			"tablet",
			"matts",
			"spell",
			"simple",
			"radical",
			"stroke_number",
			"usage_audio",
			"usage_text",
			"evolve_image",
			"observe_dot",
			"observe_arrow",
			"observe_triangle",
			"observe_image",
			"analyse_image",
			"stroke_text",
			"stroke_audio",
			"space_text",
			"space_audio",
			"technique_line",
			"technique_gesture",
			"learn_text",
			"learn_video",
			"compare_text",
			"compare_image"
	};

	//统计软笔资源
	private void tongji(String publisher, Map<String, String> map) {
		System.out.println(codeArr.length);
		List<String> codeList = new ArrayList<>(Arrays.asList(codeArr));
		List<Integer> charIds = new ArrayList<Integer>();


		for (Map.Entry<String, String> entry : map.entrySet()) {
			charIds = new ArrayList<Integer>();
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
			String name = entry.getKey();
			String font = entry.getValue();
			saveAsFileWriter("name: ***************" + name);
//			saveAsFileWriter("font: ***************" + font);

			Textbook textbook = new Textbook();
			textbook.setPublisher(publisher);
			textbook.setName(name);
			List<Textbook> list = textbookService.list(Wrappers.query(textbook));
			for (Textbook textbook1 : list) {
				Integer id = textbook1.getId();
				List<TextbookLessonVO> lessonByTextbookList = textbookLessonService.findLessonByTextbookId(id);
				for (TextbookLessonVO textbookLessonVO : lessonByTextbookList) {
					Integer lessionId = textbookLessonVO.getId();
					List<TextbookLessonCharacter> lcList = textbookLessonCharacterService.findByLessonId(lessionId);
					for (TextbookLessonCharacter textbookLessonCharacter : lcList) {
						Integer characterId = textbookLessonCharacter.getCharacterId();
						charIds.add(characterId);
					}
				}
			}

			System.out.println("总数：" + charIds.size());

			for (Integer charId : charIds) {
				CharacterResourceFile characterResourceFile = new CharacterResourceFile();
				characterResourceFile.setCharacterId(charId);
				characterResourceFile.setSubject(71);
				characterResourceFile.setFont(font);
				characterResourceFile.setIsDeleted(0);
				List<CharacterResourceFile> lists = characterResourceFileService.list(Wrappers.query(characterResourceFile));
//				if(charId == 446){
//					System.out.println(111111);
//				}
				if (lists != null && lists.size() > 0) {
					if (lists.size() < 26) {
						Character character = characterService.getById(charId);
						Integer type = character.getType();
						if (type == 1) {
							for (CharacterResourceFile resourceFile : lists) {
								Iterator<String> it = codeList.iterator();
								while (it.hasNext()) {
									String next = it.next();
									String objectId = resourceFile.getObjectId();
									if (objectId.equals(next)) {
										it.remove();
										break;
									}
								}
							}
							if ("现代简楷".equals(font)) {
								Iterator<String> it = codeList.iterator();
								while (it.hasNext()) {
									String next = it.next();
									if ("white_character".equals(next)) {
										it.remove();
									}
									if ("tablet".equals(next)) {
										it.remove();
									}
								}
							}
							if (codeList.size() > 0) {
								saveAsFileWriter(character.getCharS() + " : " + JSON.toJSONString(codeList));
							}
						}
						codeList = new ArrayList<>(Arrays.asList(codeArr));
					}
				}
			}
		}


//		Textbook textbook = new Textbook();
//		textbook.setPublisher(publisher);
//		textbook.setName("三年级上册");
//		List<Textbook> list = textbookService.list(Wrappers.query(textbook));
//		for (Textbook textbook1 : list) {
//			Integer id = textbook1.getId();
//			List<TextbookLessonVO> lessonByTextbookList = textbookLessonService.findLessonByTextbookId(id);
//			for (TextbookLessonVO textbookLessonVO : lessonByTextbookList) {
//				Integer lessionId = textbookLessonVO.getId();
//				List<TextbookLessonCharacter> lcList = textbookLessonCharacterService.findByLessonId(lessionId);
//				for (TextbookLessonCharacter textbookLessonCharacter : lcList) {
//					Integer characterId = textbookLessonCharacter.getCharacterId();
//					charIds.add(characterId);
//				}
//			}
//		}
//
//		System.out.println("总数：" + charIds.size());
//
//		for (Integer charId : charIds) {
//			CharacterResourceFile characterResourceFile = new CharacterResourceFile();
//			characterResourceFile.setCharacterId(charId);
//			characterResourceFile.setSubject(71);
//			characterResourceFile.setFont(font);
//			characterResourceFile.setIsDeleted(0);
//			List<CharacterResourceFile> lists = characterResourceFileService.list(Wrappers.query(characterResourceFile));
//			if(lists != null && lists.size() > 0){
//				if(lists.size() < 26){
//					Character character = characterService.getById(charId);
//					Integer type = character.getType();
//					if(type == 1){
//						for (CharacterResourceFile resourceFile : lists) {
//							Iterator<String> it = codeList.iterator();
//							while (it.hasNext()) {
//								String next = it.next();
//								String objectId = resourceFile.getObjectId();
//								if (objectId.equals(next)) {
//									it.remove();
//									break;
//								 }
//							}
//						}
//						saveAsFileWriter(character.getCharS() + " : " + JSON.toJSONString(codeList));
//					}
//					codeList = new ArrayList<>(Arrays.asList(codeArr));
//				}
//			}
//		}

	}

	private static String[] ybCodeArr = {
			"character",
			"spell",
			"simple",
			"radical",
			"stroke_number",
			"paraphrase_video",
			"paraphrase_text",
//			"chalk_text",
			"chalk_video",
//			"pen_text",
			"pen_video"
	};
	private static String[] ybCodeArr2 = {
			"character",
			"spell",
			"simple",
			"radical",
			"stroke_number",
			"paraphrase_video",
			"paraphrase_text",
			"chalk_video",
			"pen_text",
			"pen_video",
//			"learn_video",
			"game"
	};

	//统计硬笔资源
	private void tongjiYB() {
		Integer num = 0;
		System.out.println(ybCodeArr2.length);
//		String[] gradeCode = {"21","22"};
		String[] gradeCode = {"21", "22", "23", "24", "25", "26"};
		for (String code : gradeCode) {
			List<String> codeList = new ArrayList<>(Arrays.asList(ybCodeArr2));
			List<Integer> charIds = new ArrayList<Integer>();

			Textbook textbook = new Textbook();
			textbook.setPublisher("人民教育");
			textbook.setSubject(72);
			textbook.setGradeCode(code);
			List<Textbook> list = textbookService.list(Wrappers.query(textbook));
			for (Textbook textbook1 : list) {
				saveAsFileWriter(textbook1.getName() + " **************************");
				Integer id = textbook1.getId();
				List<TextbookLessonVO> lessonByTextbookList = textbookLessonService.findLessonByTextbookId(id);
				for (TextbookLessonVO textbookLessonVO : lessonByTextbookList) {
					saveAsFileWriter(textbookLessonVO.getName() + " **************************");
					Integer lessionId = textbookLessonVO.getId();
					List<TextbookLessonCharacter> lcList = textbookLessonCharacterService.findByLessonId(lessionId);
					for (TextbookLessonCharacter textbookLessonCharacter : lcList) {
						Integer characterId = textbookLessonCharacter.getCharacterId();
						charIds.add(characterId);

						Character character = characterService.getById(characterId);
						CharacterResourceFile characterResourceFile = new CharacterResourceFile();
						characterResourceFile.setCharacterId(characterId);
						characterResourceFile.setSubject(72);
						characterResourceFile.setIsDeleted(0);
						characterResourceFile.setObjectId("chalk_text");
						List<CharacterResourceFile> lists = characterResourceFileService.list(Wrappers.query(characterResourceFile));
						if (lists != null && lists.size() > 0) {
							CharacterResourceFile crf = lists.get(0);
							String content = crf.getContent();
							if (content == null || "".equals(content)) {
								saveAsFileWriter(character.getCharS());
								num++;
							}
						} else {
							saveAsFileWriter(character.getCharS());
							num++;
						}
					}
				}
			}

			System.out.println("总数：" + charIds.size());
			System.out.println("num：" + num);

//			for (Integer charId : charIds) {
//				CharacterResourceFile characterResourceFile = new CharacterResourceFile();
//				characterResourceFile.setCharacterId(charId);
//				characterResourceFile.setSubject(72);
//				characterResourceFile.setIsDeleted(0);
//				List<CharacterResourceFile> lists = characterResourceFileService.list(Wrappers.query(characterResourceFile));
//				if(lists != null && lists.size() > 0){
//					Character character = characterService.getById(charId);
//					for (CharacterResourceFile resourceFile : lists) {
//						Iterator<String> it = codeList.iterator();
//						while (it.hasNext()) {
//							String next = it.next();
//							String objectId = resourceFile.getObjectId();
//							if (objectId.equals(next)) {
//								it.remove();
//								break;
//							}
//						}
//					}
//					if(codeList.size()>0){
//						saveAsFileWriter(character.getCharS() + " : " + JSON.toJSONString(codeList));
//					}
//					codeList = new ArrayList<>(Arrays.asList(ybCodeArr2));
//				}
//			}
		}

	}

	//给笔画 添加 图片进 light_image
	private void addImageForWord() {
		Character character = new Character();
		character.setType(2);
		character.setIsDeleted(0);
		List<Character> list = characterService.list(Wrappers.query(character));
		if (list != null && list.size() > 0) {
			for (Character character1 : list) {
				Integer id = character1.getId();
				if (id <= 3806) {
					continue;
				}
				String charS = character1.getCharS();
				System.out.println(charS);

				CharacterResourceFile characterResourceFile = new CharacterResourceFile();
				characterResourceFile.setIsDeleted(0);
				characterResourceFile.setCharacterId(id);
				characterResourceFile.setObjectId("white_character");
				List<CharacterResourceFile> list1 = characterResourceFileService.list(Wrappers.query(characterResourceFile));
				if (list1 != null && list1.size() > 0) {
					CharacterResourceFile characterResourceFile1 = list1.get(0);
					String uuid = characterResourceFile1.getUuid();

					System.out.println(characterResourceFile1.getObjectId());
					System.out.println("uuid: " + characterResourceFile1.getUuid());
					character1.setLightImage(uuid);
					characterService.updateById(character1);
				}
			}
		}
	}

	//读取excel文件 导入笔画空间特征
	private void importFeatures(File file, String font, String code) throws IOException {
		Map<String, String> HZmap = new HashMap<String, String>();
		for (String gradeId : gradeIds) {
			if ("7073".equals(gradeId + "") || "7074".equals(gradeId + "")) {
				font = "赵体";
				continue;
			} else if ("1109".equals(gradeId + "") || "7068".equals(gradeId + "")) {
				font = "现代简楷";
			} else {
				font = "欧体";
				continue;
			}

			Word w = new Word();
			w.setCnsversion(code); //出版社code
			w.setCnnjcode(gradeId); // 年级code
			List<Word> wordList = wordService.list(Wrappers.query(w));
			if (wordList != null && wordList.size() > 0) {
				for (Word word : wordList) {
					String cnsfontname = word.getCnsfontname();
					HZmap.put(cnsfontname, font);
				}
			}
		}
		System.out.println(JSON.toJSONString(HZmap));

		//已保存汉字
		List<String> wordList = new ArrayList<String>();

		//生成表头
		String[] header = new String[3];
		header[0] = "字典";
		header[1] = "笔划特征";
		header[2] = "空间特征";

		List<Map<String, Object>> list = ExcelUtil.readEXCL(new FileInputStream(file), "xls", 0, header);
		for (Map<String, Object> map : list) {
			List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("sheetContent");
//			System.out.println(JSON.toJSONString(data));
			for (Map<String, Object> mapData : data) {
				System.out.println(mapData.get("字典"));
				System.out.println(mapData.get("笔划特征"));
				System.out.println(mapData.get("空间特征"));

				String word = (String) mapData.get("字典");
				String str1 = (String) mapData.get("笔划特征");
				String str2 = (String) mapData.get("空间特征");

				if (word != null && !"".equals(word) && !wordList.contains(word)
						&& !"".equals(str1) && !"".equals(str1)) {
					wordList.add(word);

					font = HZmap.get(word);
					System.out.println("word： " + word);
					System.out.println("font： " + font);
					if (font == null || "".equals(font)) {
						continue;
					}

					//笔画特征
					characterResourceService.createSoftResourceFile(word, 714, font, "stroke_text", str1);
					//空间特征
					characterResourceService.createSoftResourceFile(word, 714, font, "space_text", str2);

					String fileName = word + "_笔画特征_" + font;
					File fMp3 = new File("C:\\Users\\80969\\Desktop\\temp\\4\\" + fileName + ".mp3");
					if (!fMp3.exists()) {
						TextTransAudio.trans(str1, fileName, "C:\\Users\\80969\\Desktop\\temp\\4");
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fileName = word + "_空间特征_" + font;
					fMp3 = new File("C:\\Users\\80969\\Desktop\\temp\\4\\" + fileName + ".mp3");
					if (!fMp3.exists()) {
						TextTransAudio.trans(str2, fileName, "C:\\Users\\80969\\Desktop\\temp\\4");
					}
				}

			}
		}
	}

	//导入语音文件
	private void importAudio(File f1, String font, boolean bool) throws IOException {
		if (f1.isDirectory()) {
			File[] f2 = f1.listFiles();
			for (int i = 0; i < f2.length; i++) {
				System.out.println((i + 1) + " **************************************");
				File file = f2[i];
				if (file.isFile()) {
					String fileExtension = FileUtil.getFileExtension(file.getName());
					String fileName = file.getName().replaceAll("." + fileExtension, "");

					String name;
					if (bool) {
						String[] s = fileName.split("_");
						name = s[0];
						font = s[1];
					} else {
						String[] names = fileName.split("_");
						name = names[0];
						font = names[2];
					}
					if (!"恒".equals(name)) {
						continue;
					}
					EntityFile entityFile = entityFileClient.upload(file);
					if (entityFile != null) {
						String uuid = entityFile.getUuid();
						if (bool) {
							characterResourceService.createSoftResourceFile(name, 712, font, "usage_audio", uuid);
						} else {
							if (fileName.contains("笔画")) {
								characterResourceService.createSoftResourceFile(name, 714, font, "stroke_audio", uuid);
							} else {
								characterResourceService.createSoftResourceFile(name, 714, font, "space_audio", uuid);
							}
						}
					}
				}
			}
		}
	}

	//年级ids
	private static String[] gradeIds = {"1109", "7068", "7069", "7070", "7071", "7072", "7073", "7074"};

	//导入文本2
	private void importTxt2(String code, String font) {
		Integer i = 0;
		for (String gradeId : gradeIds) {
			if ("7073".equals(gradeId + "") || "7074".equals(gradeId + "")) {
				font = "赵体";
			} else if ("1109".equals(gradeId + "") || "7068".equals(gradeId + "")) {
				font = "现代简楷";
			} else {
				font = "欧体";
			}
			Video video = new Video();
			video.setCnsversion(code); //出版社code
			video.setCnnjcode(gradeId); // 年级code
			List<VideoVO> videoList = videoService.selectVideo(code, gradeId);
			if (videoList != null && videoList.size() > 0) {
				for (VideoVO video1 : videoList) {
					if (video1 == null) {
						continue;
					}
					String hzname = video1.getCharaterName();
					String content = video1.getContent();

//					if("后（繁体）".equals(hzname)){
//						hzname = "後";
//					} else if("云（繁体）".equals(hzname)){
//						hzname = "雲";
//					}
//					if(!"後".equals(hzname) && !"雲".equals(hzname)){
//						continue;
//					}

					if (hzname != null && content != null && !"".equals(content)) {
						int index = hzname.indexOf("（");
						if (index != -1) {
							hzname = hzname.substring(0, index);
						}

						String textFromHtml = HtmlUtil.getTextFromHtml(video1.getContent());

						textFromHtml = textFromHtml.replaceAll("书写要点：", "").replaceAll("书写提要：", "");

						i++;
						System.out.println(i);
						characterResourceService.createSoftResourceFile(hzname, 716, font, "learn_text", textFromHtml);
					}
				}
			}
		}
	}

	//导入文本
	private void importTxt(String code, String font) {
		List<String> words = new ArrayList<String>();

		for (String gradeId : gradeIds) {
			if ("7073".equals(gradeId) || "7074".equals(gradeId)) {
				font = "赵体";
				continue;
			} else if ("1109".equals(gradeId) || "7068".equals(gradeId)) {
				font = "现代简楷";
				continue;
			} else {
				font = "欧体";

			}


			Word w = new Word();
			w.setCnsversion(code); //出版社code
			w.setCnnjcode(gradeId); // 年级code
			List<Word> wordList = wordService.list(Wrappers.query(w));
			if (wordList != null && wordList.size() > 0) {
				for (Word word : wordList) {

					System.out.println(word.getCnspinyinzm()); //拼音
					System.out.println(word.getCnssimplified()); //简体
					System.out.println(word.getCnsradical()); //部首
					System.out.println(word.getCnnstroke()); //笔画
					System.out.println(word.getCnsexplanation()); //用法
//				System.out.println("转语音：  " + word.getCnsexplanation()); //用法
//
//				System.out.println("分析：笔画特征文案：  "); //
//				System.out.println("分析：笔画特征语音：  "); //
//				System.out.println("分析：空间特征文案：  "); //
//				System.out.println("分析：空间特征语音：  "); //
//				System.out.println(); //视频解析文案

					String characterName = word.getCnsfontname();
					String pinyin = word.getCnspinyinzm();
					String cnssimplified = word.getCnssimplified();
					String cnsradical = word.getCnsradical();
					String cnnstroke = word.getCnnstroke();
					String cnsexplanation = word.getCnsexplanation();

//					if("后（繁体）".equals(characterName)){
//						characterName = "後";
//					} else if("云（繁体）".equals(characterName)){
//						characterName = "雲";
//					}
					if (!"恒".equals(characterName)) {
						continue;
					}

					if (pinyin != null && !"".equals(pinyin)) {
						//拼音
						characterResourceService.createSoftResourceFile(characterName, 711, font, "spell", pinyin);
						//读音
						characterResourceService.createSoftResourceFile(characterName, 712, font, "spell", pinyin);
					}
					if (cnssimplified != null && !"".equals(cnssimplified)) {
						//简体
						characterResourceService.createSoftResourceFile(characterName, 712, font, "simple", cnssimplified);
					}
					if (cnsradical != null && !"".equals(cnsradical)) {
						//部首
						characterResourceService.createSoftResourceFile(characterName, 712, font, "radical", cnsradical);
					}
					if (cnnstroke != null && !"".equals(cnnstroke)) {
						//笔画
						characterResourceService.createSoftResourceFile(characterName, 712, font, "stroke_number", cnnstroke);
					}
					//用法 -- 过滤html标签
					if (cnsexplanation != null && !"".equals(cnsexplanation) && !words.contains(characterName)) {
						words.add(characterName);
						String textFromHtml = HtmlUtil.getTextFromHtml(cnsexplanation);
						System.out.println("过滤标签后： ");
						System.out.println(textFromHtml);

						textFromHtml = textFromHtml.replaceAll("\\.", "。").replaceAll(",", "。").replaceAll(":", "：");

						characterResourceService.createSoftResourceFile(characterName, 712, font, "usage_text", textFromHtml);

						File fMp3 = new File("C:\\Users\\80969\\Desktop\\temp\\2\\" + characterName + "_" + font + ".mp3");
						if (!fMp3.exists()) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

//						文本转语音
							TextTransAudio.trans(textFromHtml, characterName + "_" + font, "C:\\Users\\80969\\Desktop\\temp\\2");
						}
//						characterResourceService.createSoftResourceFile(characterName, 712, "颜勤", "usage_audio", textFromHtml);
					}
				}
			}
		}
	}

	private static String uuids = "";
	private static String names = "";

	private void importResource(File f1, String font) throws IOException {
		if (f1.isDirectory()) {
			File[] f2 = f1.listFiles();
			for (int i = 0; i < f2.length; i++) {
				File file = f2[i];
				if (file.isFile()) {
					String fileExtension = FileUtil.getFileExtension(file.getName());
					if ("png".equals(fileExtension) || "jpg".equals(fileExtension) || "gif".equals(fileExtension)
							|| "flv".equals(fileExtension) || "mp4".equals(fileExtension)) {
						String path = file.getPath();
						String resultDirectory = path.replaceAll("\\\\", "/");
						String arr[] = resultDirectory.split("/");
						String name = arr[arr.length - 1]; //当前文件名
						String parentName = arr[arr.length - 2]; //上级文件名
						String characterName = arr[arr.length - 3]; //上上级文件名
						String gradeName = arr[arr.length - 5]; //年级文件名

//						if(gradeName.contains("六年级上册")||gradeName.contains("六年级下册")){
//							font = "赵体";
//						} else if(gradeName.contains("三年级上册")||gradeName.contains("三年级下册")){
//							font = "现代简楷";
//						} else {
//							font = "欧体";
//						}
						System.out.println(gradeName + " ********************* " + font);

						String objectCode = judgeResource(name);
						if (objectCode == null) {
							continue;
						}
						Integer resourceType = getResourceTypeByCode(objectCode);

//						if(!"走之儿".equals(characterName)){
//							continue;
//						}

						EntityFile entityFile = uploadFile(file, objectCode);
						if (entityFile != null) {
							String uuid = entityFile.getUuid();
							if ("compare_image".equals(objectCode)) {
								String name_new = name.replaceAll(".jpg", "").replaceAll(".png", "");
								if ("".equals(uuids)) {
									names = name_new;
									uuids = uuid;
								} else {
									names = names + "," + name_new;
									uuids = uuids + "," + uuid;
								}

								if (i == (f2.length - 1)) {
									characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, uuids);
									objectCode = "compare_text";
									resourceType = getResourceTypeByCode(objectCode);
									characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, names);
									names = "";
									uuids = "";
								}
							} else if ("practice_images".equals(objectCode)) {
								if ("".equals(uuids)) {
									uuids = uuid;
								} else {
									uuids = uuids + "," + uuid;
								}

								if (i == (f2.length - 1)) {
									characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, uuids);
									uuids = "";
								}
							} else {
								characterResourceService.createSoftResourceFile(characterName, resourceType, font, objectCode, uuid);
							}
						}
					}
				}
				if (f1.isDirectory()) {
					importResource(file, font); //递归调用
				}
			}
		}
	}

	//判断资源类型
	private String judgeResource(String name) {
		String code = null;
		if (name.contains("碑帖欣赏.png") || name.contains("碑帖欣赏.jpg")) { //黑体白字图
			code = "white_character";
		} else if (name.contains("碑帖缩落.png") || name.contains("碑帖缩落.jpg")) { //源碑文
			code = "tablet";
		} else if (name.contains("米底空心字.png") || name.contains("米底空心字.jpg")) { //米字格图
			code = "matts";
		} else if (name.contains("六书演变.png") || name.contains("六书演变.jpg")) { //汉字演变图
			code = "evolve_image";
		} else if (name.contains("认读音频")) { //汉字演变图
			code = "usage_audio";
		}  else if (name.contains("字形结构.png") || name.contains("字形结构.jpg")) { //字形特征图
			code = "observe_image";
		} else if (name.contains("字形特征1")) { //点-米字图
			code = "observe_dot";
		} else if (name.contains("字形特征2")) { //箭头-米字图
			code = "observe_arrow";
		} else if (name.contains("字形特征3")) { //三角-米字图
			code = "observe_triangle";
		} else if (name.contains("笔法分析.png") || name.contains("笔法分析.jpg")) { //字体分析图
			code = "analyse_image";
		} else if (name.contains("笔画特征文本.txt")) { //笔画特征文本
			code = "stroke_text";
		} else if (name.contains("笔画特征.mp3")) { //笔画特征语音
			code = "stroke_audio";
		} else if (name.contains("空间特征文本.txt")) { //笔画特征语音
			code = "space_text";
		} else if (name.contains("空间特征.mp3")) { //空间特征.mp3
			code = "space_audio";
		} else if (name.contains("行笔路线.gif")) { //行笔路线视频
			code = "technique_line";
		} else if (name.contains("书写动画.gif")) { //书写动画视频
			code = "technique_gesture";
		} else if (name.contains(".flv") || name.contains(".mp4")) { //视频
			code = "learn_video";
		} else if (name.contains("对比")) { //图片
			code = "compare_image";
		} else if (name.contains("随堂练习")) { //图片
			code = "practice_images";
		}
		return code;
	}

	//根据code 返回 resource_type
	private Integer getResourceTypeByCode(String code) {
		Integer type = null;
		if ("white_character".equals(code)) {
			type = 711;
		} else if ("tablet".equals(code)) {
			type = 711;
		} else if ("matts".equals(code)) {
			type = 712;
		} else if ("evolve_image".equals(code)) {
			type = 712;
		} else if ("observe_image".equals(code)) {
			type = 713;
		} else if ("observe_dot".equals(code)) {
			type = 713;
		} else if ("observe_arrow".equals(code)) {
			type = 713;
		} else if ("observe_triangle".equals(code)) {
			type = 713;
		} else if ("analyse_image".equals(code)) {
			type = 714;
		} else if ("technique_line".equals(code)) {
			type = 715;
		} else if ("technique_gesture".equals(code)) {
			type = 715;
		} else if ("learn_video".equals(code)) {
			type = 716;
		} else if ("compare_text".equals(code)) {
			type = 717;
		} else if ("compare_image".equals(code)) {
			type = 717;
		} else if ("practice_images".equals(code)) {
			type = 718;
		}
		return type;
	}

	//上传文件
	private EntityFile uploadFile(File file, String code) throws IOException {
		if (code == null || "".equals(code)) {
			return null;
		}
		EntityFile entityFile = null;
		if ("technique_line".equals(code) || "technique_gesture".equals(code) || "learn_video".equals(code)) {
			entityFile = entityFileClient.upload(file);
		} else {
			entityFile = entityFileClient.uploadImage(file);
		}

		return entityFile;
	}

	private void importResourceFile(File f1) throws IOException {
		if (f1.isDirectory()) {
			File[] f2 = f1.listFiles();
			for (File file : f2) {
				if (file.isFile()) {
					String fileExtension = FileUtil.getFileExtension(file.getName()); //
					if ("mp4".equals(fileExtension) || "flv".equals(fileExtension) || "png".equals(fileExtension) || "txt".equals(fileExtension) || "mp3".equals(fileExtension)) { //mp4 或 flv
						String strParentDirectory = file.getParent();
						String resultParentDirectory = strParentDirectory.replaceAll("\\\\", "/");
						String arr[] = resultParentDirectory.split("/");
						String name = arr[arr.length - 1]; //父级文件夹名 即文字

						System.out.println("文件的父级目录为 : " + name);

						//查询文字是否存在
						Character character = characterService.findCharacterByKeyword(name);
						System.out.println(character);

						Integer flag = null; // 0 = 图片; 1=音频; 2=文本

						if (character != null) { //与字典表中存在

							if ("txt".equals(fileExtension)) { //读取 文本数据
								flag = 2;

							} else if ("png".equals(fileExtension)) { //上传 oss
								flag = 0;

							} else if ("flv".equals(fileExtension)) { //上传视频点播
								flag = 1;

								if ("钢笔".equals(fileExtension)) {

								} else if ("粉笔".equals(fileExtension)) {

								} else if ("识字".equals(fileExtension)) {

								}

							} else if ("mp3".equals(fileExtension)) { //上传文本音频
								flag = 1;

							}

//                            String[] split = file.getPath().replaceAll("\\\\", "/").split("/");
//                            String word = split[split.length - 1];
//
//                            saveAsFileWriter(name + " : " + file);
//
							if (flag != null) {
								EntityFile entityFile = null;
								if (flag == 0) { //图片
									entityFile = entityFileClient.uploadImage(file);

								} else if (flag == 1) { //音频
									entityFile = entityFileClient.upload(file);

								} else if (flag == 2) { //文本

								}

								CharacterResource characterResource = characterResourceService.findUnion(character.getId(), 72, 5);
								if (entityFile != null && characterResource != null) {
									Integer resourceId = characterResource.getId();
									CharacterResourceFile characterResourceFile = characterResourceFileService.findUnionByResourceIdAndObjectId(resourceId, "learn", "");
									if (characterResourceFile == null) {
										characterResourceFile = new CharacterResourceFile();
										characterResourceFile.setResourceId(resourceId);
										characterResourceFile.setCharacterId(character.getId());
										characterResourceFile.setSubject(72);
										characterResourceFile.setResourceType(5);
										characterResourceFile.setObjectId("learn");
										characterResourceFile.setObjectType("video");
										characterResourceFile.setUuid(entityFile.getUuid());
										characterResourceFile.setIsDeleted(0);
										characterResourceFile.setCreateDate(LocalDateTime.now());
										characterResourceFile.setModifyDate(LocalDateTime.now());
										characterResourceFileService.save(characterResourceFile);
									}
								} else if (entityFile != null && characterResource == null) {
									characterResource = new CharacterResource();
									characterResource.setCharacterId(character.getId());
									characterResource.setNameTr(name);
									characterResource.setCharT(name);
									characterResource.setCharS(name);
									characterResource.setKeyword(name);
									characterResource.setResourceType(5);
									characterResource.setVisitedCount(0);
									characterResource.setSubject(72);
									characterResource.setEnabled(true);
									characterResource.setIsDeleted(0);
									characterResource.setCreateDate(LocalDateTime.now());
									characterResource.setCreateDate(LocalDateTime.now());
									characterResourceService.save(characterResource);

									CharacterResourceFile characterResourceFile = new CharacterResourceFile();
									characterResourceFile.setResourceId(characterResource.getId());
									characterResourceFile.setCharacterId(character.getId());
									characterResourceFile.setSubject(72);
									characterResourceFile.setResourceType(5);
									characterResourceFile.setObjectId("learn");
									characterResourceFile.setObjectType("video");
									characterResourceFile.setUuid(entityFile.getUuid());
									characterResourceFile.setIsDeleted(0);
									characterResourceFile.setCreateDate(LocalDateTime.now());
									characterResourceFile.setModifyDate(LocalDateTime.now());
									characterResourceFileService.save(characterResourceFile);
								}
							}
						}
					}
				}
				if (f1.isDirectory()) {
					importResourceFile(file); //递归调用
				}
			}
		}
	}


	private static void copy(File fileOld, String url2) throws Exception {
		FileInputStream in = new FileInputStream(fileOld);
		FileOutputStream out = new FileOutputStream(new File(url2));
		byte[] buff = new byte[1024];
		int n = 0;
//        System.out.println("复制文件：" + "\n" + "源路径：" + url1 + "\n" + "目标路径："
//                + url2);
		while ((n = in.read(buff)) != -1) {
			out.write(buff, 0, n);
		}
		out.flush();
		in.close();
		out.close();
		System.out.println("复制完成");
	}


	private static void saveAsFileWriter(String content) {
		FileWriter fwriter = null;
		try {
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
			fwriter = new FileWriter("C:\\Users\\80969\\Desktop\\test.txt", true);
			fwriter.write(content);
			fwriter.write("\r\n");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	//根据年级名返回gradeCode
	private String judgeGradeCode(String gradeName) {
		if (gradeName.contains("一年级")) {
			return "21";
		} else if (gradeName.contains("二年级")) {
			return "22";
		} else if (gradeName.contains("三年级")) {
			return "23";
		} else if (gradeName.contains("四年级")) {
			return "24";
		} else if (gradeName.contains("五年级")) {
			return "25";
		} else if (gradeName.contains("六年级")) {
			return "26";
		} else if (gradeName.contains("七年级")) {
			return "31";
		} else if (gradeName.contains("八年级")) {
			return "32";
		} else if (gradeName.contains("九年级")) {
			return "33";
		}
		return null;
	}

	//根据年级名返回Volume
	private String judgeVolume(String gradeName) {
		if (gradeName.contains("上册")) {
			return "1";
		} else if (gradeName.contains("下册")) {
			return "2";
		}
		return null;
	}

	//根据单元名返回section
	private Integer judgeSection(String sectionName) {
		if (sectionName.contains("第一")) {
			return 1;
		} else if (sectionName.contains("第二")) {
			return 2;
		} else if (sectionName.contains("第三")) {
			return 3;
		} else if (sectionName.contains("第四")) {
			return 4;
		} else if (sectionName.contains("第五")) {
			return 5;
		} else if (sectionName.contains("第六")) {
			return 6;
		}
		return null;
	}


	private static Map<Integer, String> lessonMap = new LinkedHashMap<Integer, String>();
	private static Map<Integer, String> gradeMap = new LinkedHashMap<Integer, String>();

	static {
//		gradeMap.put(5809314, "一年级上册");
//		gradeMap.put(7126000, "一年级下册");
//		gradeMap.put(5809316, "二年级上册");
//		gradeMap.put(7126001, "二年级下册");
		gradeMap.put(1109, "三年级上册");
		gradeMap.put(7068, "三年级下册");
		gradeMap.put(7069, "四年级上册");
		gradeMap.put(7070, "四年级下册");
		gradeMap.put(7071, "五年级上册");
		gradeMap.put(7072, "五年级下册");
		gradeMap.put(7073, "六年级上册");
		gradeMap.put(7074, "六年级下册");
		gradeMap.put(7931156, "七年级上册");
		gradeMap.put(7931157, "七年级下册");
		gradeMap.put(7931158, "八年级上册");
		gradeMap.put(7931159, "八年级下册");
		gradeMap.put(7931160, "九年级上册");
		gradeMap.put(7931161, "九年级下册");

		lessonMap.put(1544300, "学习与运用一");
		lessonMap.put(1544301, "学习与运用二");
		lessonMap.put(1544302, "学习与运用三");
		lessonMap.put(1544303, "学习与运用四");
		lessonMap.put(1103, "第一课");
		lessonMap.put(7075, "第二课");
		lessonMap.put(7076, "第三课");
		lessonMap.put(7077, "第四课");
		lessonMap.put(7078, "第五课");
		lessonMap.put(7079, "第六课");
		lessonMap.put(7080, "第七课");
		lessonMap.put(7081, "第八课");
		lessonMap.put(7082, "第九课");
		lessonMap.put(7083, "第十课");
		lessonMap.put(7084, "第十一课");
		lessonMap.put(7085, "第十二课");
		lessonMap.put(7086, "第十三课");
		lessonMap.put(7087, "第十四课");
		lessonMap.put(7088, "第十五课");
		lessonMap.put(7089, "第十六课");
		lessonMap.put(101850, "第十七课");
		lessonMap.put(2617251, "第十八课");
		lessonMap.put(5809302, "第十九课");
		lessonMap.put(5809303, "第二十课");
		lessonMap.put(5809304, "第二十一课");
		lessonMap.put(5809305, "第二十二课");
		lessonMap.put(5809306, "第二十三课");
		lessonMap.put(5809307, "第二十四课");
		lessonMap.put(5809308, "第二十五课");
		lessonMap.put(5809309, "第二十六课");
		lessonMap.put(5809310, "第二十七课");
		lessonMap.put(5809311, "第二十八课");
		lessonMap.put(5809312, "第二十九课");
		lessonMap.put(5809317, "第三十课");
		lessonMap.put(5809318, "第三十一课");
		lessonMap.put(5809319, "第三十二课");
		lessonMap.put(5809320, "第三十三课");
		lessonMap.put(5809321, "第三十四课");
		lessonMap.put(5809322, "第三十五课");
		lessonMap.put(5809323, "第三十六课");
		lessonMap.put(5809324, "第三十七课");
		lessonMap.put(7909808, "第三十八课");
		lessonMap.put(7909809, "第三十九课");
		lessonMap.put(7909810, "第四十课");
		lessonMap.put(7909811, "第四十一课");
		lessonMap.put(7909812, "第四十二课");

	}

	//根据课程名名返回listOrder
	private Integer judgeListOrder(String lessonName) {
		if (lessonName.contains("第一课")) {
			return 1;
		} else if (lessonName.contains("第二课")) {
			return 2;
		} else if (lessonName.contains("第三课")) {
			return 3;
		} else if (lessonName.contains("第四课")) {
			return 4;
		} else if (lessonName.contains("第五课")) {
			return 5;
		} else if (lessonName.contains("第六课")) {
			return 6;
		} else if (lessonName.contains("第七课")) {
			return 7;
		} else if (lessonName.contains("第八课")) {
			return 8;
		} else if (lessonName.contains("第九课")) {
			return 9;
		} else if (lessonName.contains("第十课")) {
			return 10;
		} else if (lessonName.contains("第十一课")) {
			return 11;
		} else if (lessonName.contains("第十二课")) {
			return 12;
		} else if (lessonName.contains("第十三课")) {
			return 13;
		} else if (lessonName.contains("第十四课")) {
			return 14;
		} else if (lessonName.contains("第十五课")) {
			return 15;
		} else if (lessonName.contains("第十六课")) {
			return 16;
		} else if (lessonName.contains("第十七课")) {
			return 17;
		} else if (lessonName.contains("第十八课")) {
			return 18;
		} else if (lessonName.contains("第十九课")) {
			return 19;
		} else if (lessonName.contains("第二十课")) {
			return 20;
		} else if (lessonName.contains("第二十一")) {
			return 21;
		} else if (lessonName.contains("第二十二")) {
			return 22;
		} else if (lessonName.contains("第二十三")) {
			return 23;
		} else if (lessonName.contains("第二十四")) {
			return 24;
		} else if (lessonName.contains("二十五")) {
			return 25;
		} else if (lessonName.contains("二十六")) {
			return 26;
		} else if (lessonName.contains("二十七")) {
			return 27;
		} else if (lessonName.contains("二十八")) {
			return 28;
		} else if (lessonName.contains("二十九")) {
			return 29;
		} else if (lessonName.contains("三十")) {
			return 30;
		} else if (lessonName.contains("三十一")) {
			return 31;
		} else if (lessonName.contains("三十二")) {
			return 32;
		} else if (lessonName.contains("三十三")) {
			return 33;
		} else if (lessonName.contains("三十四")) {
			return 34;
		} else if (lessonName.contains("三十五")) {
			return 35;
		} else if (lessonName.contains("三十六")) {
			return 36;
		} else if (lessonName.contains("三十七")) {
			return 37;
		} else if (lessonName.contains("三十八")) {
			return 38;
		} else if (lessonName.contains("三十九")) {
			return 39;
		} else if (lessonName.contains("四十")) {
			return 40;
		} else if (lessonName.contains("四十一")) {
			return 41;
		} else if (lessonName.contains("学习与运用一")) {
			return 101;
		} else if (lessonName.contains("学习与运用二")) {
			return 102;
		} else if (lessonName.contains("学习与运用三")) {
			return 103;
		} else if (lessonName.contains("学习与运用四")) {
			return 104;
		} else {
			return 100;
		}
	}

	public static void main(String[] args) {
		String str = "横取斜势，左低右高。该字多撇宜取直撇，方向大致相同，长短有变化。以竖中线为中轴，分为左右大致对称分布。以长横定字宽，左右对称均匀。";
		TextTransAudio.trans(str, "率_笔画特征", "C:\\Users\\80969\\Desktop\\temp");
		str = "该字笔画之间有两组大小大致相等的空间构成，宜注意空间的平均布局。该字左右空间分布均匀。";
		TextTransAudio.trans(str, "率_空间特征", "C:\\Users\\80969\\Desktop\\temp");
	}

//	public static void main(String[] args) {
//		File f = new File("F:\\SpringBladeToB\\resource\\zhaocha");   //假设要列出 F:/A 下的文件（夹）
//		treefiles(f, 1);
//	}
//	static String endString = "|__";  //结尾字符串，用来形象显示层次关系
//	public static void treefiles(File f1, int level) {
//		String preString = "";
//		for (int i = 0; i < level; i++) {
//			preString = "   " + preString; //每增加一层，加三个空格和结尾字符串 |__
//		}
//		preString += endString;  //加上结尾字符串 |__
//		if (f1.isDirectory()) {
//			File[] f2 = f1.listFiles();
//			if(f1.getName().contains("找茬") && f2.length == 0){
//				saveAsFileWriter(f1.getPath());
//			}
//			for (int i = 0; i < f2.length; i++) {
//
//				String temp = preString + f2[i].getName();
//				System.out.println(temp);
//				if (f1.isDirectory())
//					treefiles(f2[i], level + 1); //递归调用
//			}
//		}
//	}

	//获取流文件
	private static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除本地临时文件
	 *
	 * @param file
	 */
	public static void delteTempFile(File file) {
		if (file != null) {
			boolean delete = file.delete();
			System.out.println("删除：" + delete);
		}
	}

	//批量修改文本
	private void modifyTEXT() {
		CharacterResourceFile characterResourceFile = new CharacterResourceFile();
		characterResourceFile.setObjectId("usage_text");
//		characterResourceFile.setFont("欧体");
		characterResourceFile.setIsDeleted(0);
		List<CharacterResourceFile> list = characterResourceFileService.list(Wrappers.query(characterResourceFile));
		if (list != null && list.size() > 0) {
			for (CharacterResourceFile resourceFile : list) {
				String content = resourceFile.getContent();
				if (!content.contains("。2")) {
					System.out.println(content);
					String[] split = content.split("2");
					if (split.length > 1) {
						String s = split[0] + "。2" + split[1];
						System.out.println(s);
						resourceFile.setContent(s);
//						characterResourceFileService.updateById(resourceFile);
					}
				}
			}
		}
	}

	//随堂练习资源补充
	private void practiceResource(String publisher, String font) {
		//查询出教材关联的汉字
		Textbook textbook = new Textbook();
		textbook.setPublisher(publisher);
		List<Textbook> list = textbookService.list(Wrappers.query(textbook));

		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer, String> map = new IdentityHashMap<Integer, String>();
		for (Textbook textbook1 : list) {
			if ("六年级上册".equals(textbook1.getName()) || "六年级下册".equals(textbook1.getName())) {
				font = "颜勤";
			} else {
				font = "欧体";
			}
			Integer id = textbook1.getId();
			List<TextbookLessonVO> lessonByTextbookList = textbookLessonService.findLessonByTextbookId(id);
			for (TextbookLessonVO textbookLessonVO : lessonByTextbookList) {
				String name = textbookLessonVO.getName();
				String chars = textbookLessonVO.getChars();

				String[] split = chars.split("、");
				for (String characterName : split) {
					Character character = characterService.findCharacterByKeyword(characterName);
					CharacterResource characterResource = characterResourceService.findUnion(character.getId(), 71, 717);
					if (characterResource != null) {
						CharacterResourceFile characterResourceFile = characterResourceFileService.findUnionByResourceIdAndObjectId(characterResource.getId(), "compare_text", font);
						if (characterResourceFile != null) {
							String content = characterResourceFile.getContent();
							String fontName = "对比欧体";
							if ("颜勤".equals(font)) {
								fontName = "对比颜勤";
							}
							if (content.contains(fontName)) {
								ids.add(character.getId());
								map.put(character.getId(), font);
							} else {
//								saveAsFileWriter(font + " : " + characterResource.getNameTr());
							}
						} else {
//							saveAsFileWriter(characterResource.getNameTr());
						}
					} else {
//						saveAsFileWriter(character.getCharS());
					}
				}
			}
		}

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			Integer id = entry.getKey();
			font = entry.getValue();

			String result = "";
//			white_character
			Character character = characterService.getById(id);

			CharacterResource characterResource = characterResourceService.findUnion(id, 71, 711);
			CharacterResourceFile characterResourceFile = characterResourceFileService.findUnionByResourceIdAndObjectId(characterResource.getId(), "white_character", font);
			if (characterResourceFile != null) {
				String uuid = characterResourceFile.getUuid();
				result += uuid + ",";
			} else {
				saveAsFileWriter(characterResource.getNameTr());
			}

			//对比颜勤
			boolean bool = false;
			characterResource = characterResourceService.findUnion(id, 71, 717);
			characterResourceFile = characterResourceFileService.findUnionByResourceIdAndObjectId(characterResource.getId(), "compare_text", font);
			if (characterResourceFile != null) {
				String content = characterResourceFile.getContent();
				String[] split = content.split(",");
				Integer index = null;
				String fontName = "对比欧体";
				if ("颜勤".equals(font)) {
					fontName = "对比颜勤";
				}
				for (int i = 0; i < split.length; i++) {
					String name = split[i];
					if (fontName.equals(name)) {
						index = i;
					}
				}
				if (index != null) {
					characterResource = characterResourceService.findUnion(id, 71, 717);
					characterResourceFile = characterResourceFileService.findUnionByResourceIdAndObjectId(characterResource.getId(), "compare_image", font);
					String content1 = characterResourceFile.getContent();
					String[] split1 = content1.split(",");
					String uuid = split1[index];
					result += uuid + ",";
					result += uuid + ",";
					bool = true;
				} else {
					saveAsFileWriter(characterResource.getNameTr());
				}
			} else {
				saveAsFileWriter(characterResource.getNameTr());
			}

			//matts
			characterResource = characterResourceService.findUnion(id, 71, 712);
			characterResourceFile = characterResourceFileService.findUnionByResourceIdAndObjectId(characterResource.getId(), "matts", font);
			if (characterResourceFile != null) {
				String uuid = characterResourceFile.getUuid();
				result += uuid;
			} else {
				saveAsFileWriter(font + " : " + character.getCharS());
			}
//			saveAsFileWriter("result: " + result);
			saveAsFileWriter(id + " : " + font);

			if(!"".equals(result) && bool){
				characterResourceService.createSoftResourceFile(character.getCharS(), 718, font, "practice_images", result);
			}
		}
	}
}
