package com.mteam.nutri.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.bean.NutritionCategoryBean;
import com.mteam.nutri.bean.WorkoutBean;
import com.mteam.nutri.bean.WorkoutDay;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {
	public static final String FXP_PREFS = "FxpHulaHoop";
	
	public static String getBasicText(String input) {
		String tmp = input.replace(" ", "");
		tmp = tmp.replace("-", "");

		return tmp;
	}

	public static int getId(String seed,
			@SuppressWarnings("rawtypes") Class type) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Field field = type.getField(seed);

		int id = field.getInt(null);

		return id;
	}

	public static void saveData(Activity activity, String key, String data) {
		SharedPreferences settings = activity.getSharedPreferences(FXP_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, data);
		editor.commit();
	}

	public static String loadData(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(FXP_PREFS, 0);
	    String data = settings.getString(key, "");
	    return data;
	}
	
	public static boolean isTablet(Activity activity) {
		int screenSize = activity.getResources().getConfiguration().screenLayout &
		        Configuration.SCREENLAYOUT_SIZE_MASK;
		
		boolean result = false;
		
		switch(screenSize) {
			case Configuration.SCREENLAYOUT_SIZE_XLARGE:
		    case Configuration.SCREENLAYOUT_SIZE_LARGE:
		    	result = true;
		    	break;
		    case Configuration.SCREENLAYOUT_SIZE_NORMAL:
		    case Configuration.SCREENLAYOUT_SIZE_SMALL:
		    	result = false;
		    	break;
		}
		
		return result;
	}
	
	public static Map<String, List<WorkoutBean>> loadMainWorkoutsMap(Context context) {
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, List<WorkoutBean>> workoutMap = new HashMap<String, List<WorkoutBean>>();
		try {
			String[] assetFileNames = context.getAssets().list("datas");
			for (String fileName : assetFileNames) {
				if (fileName.indexOf("main_") == 0) {
					InputStream is = context.getAssets().open("datas/" + fileName);			
					Document document = (Document) builder.build(is);
					Element rootNode = document.getRootElement();			
					loadWorkoutsToMap(rootNode.getChildren("workout"), workoutMap, false);		
				}
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		
		return workoutMap;
	}
	
	public static Map<String, List<WorkoutBean>> loadPremiumWorkoutsMap(Context context) {
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, List<WorkoutBean>> workoutMap = new HashMap<String, List<WorkoutBean>>();
		try {
			String[] assetFileNames = context.getAssets().list("datas");
			for (String fileName : assetFileNames) {
				if (fileName.indexOf("premium_") == 0) {
					InputStream is = context.getAssets().open("datas/" + fileName);			
					Document document = (Document) builder.build(is);
					Element rootNode = document.getRootElement();			
					loadWorkoutsToMap(rootNode.getChildren("workout"), workoutMap, true);		
				}
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		
		return workoutMap;
	}
	
	public static Map<String, ExerciseBean> loadAvaiableExerices (Context context) {
		SAXBuilder builder = new SAXBuilder();
		Map<String, ExerciseBean> exercisesMap = new HashMap<String, ExerciseBean>();
		try {
			InputStream is = context.getAssets().open("datas/exercises.xml");
			Document document = (Document) builder.build(is);
			Element rootNode = document.getRootElement();
			List<Element> exercises = rootNode.getChildren("exercise");
			
			for (int i = 0; i < exercises.size(); i++) {
				Element exercise = (Element)exercises.get(i);	
				String exerciseName = exercise.getAttributeValue("name");
				String exercseId = exercise.getChildText("iconId");
				String description = exercise.getChildText("description");
				List<String> muscleGroupItTargets = new ArrayList<String>();
				List<Element> xmlMuscleGroupsItTargets = ((Element)exercise.getChildren("muscleGroupsItTargets").get(0)).getChildren("muscleGroup");
				for (int j = 0; j < xmlMuscleGroupsItTargets.size(); j++) {
					Element muscleGroup = xmlMuscleGroupsItTargets.get(j);
					String group = muscleGroup.getText();
					muscleGroupItTargets.add(group);
				}
				String youtubeURL = exercise.getChildText("youtubeURL");
				String note = exercise.getChildText("note");
				String block = exercise.getChildText("block");
				String level = exercise.getChildText("level");
				String focus = exercise.getChildText("focus");
				ExerciseBean bean = new ExerciseBean(exercseId, exerciseName, description, youtubeURL, note, block, level, focus, muscleGroupItTargets);
				exercisesMap.put(exerciseName, bean);
			}
						
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return exercisesMap;
	}
	
	public static Map<String, NutritionCategoryBean> loadNutritionGuide (Context context) {
		SAXBuilder builder = new SAXBuilder();
		Map<String, NutritionCategoryBean> nutritionCategoryMap = new HashMap<String, NutritionCategoryBean>();
		try {
			InputStream is = context.getAssets().open("datas/nutrition.xml");
			Document document = (Document) builder.build(is);
			Element rootNode = document.getRootElement();
			List<Element> categories = rootNode.getChildren("category");
			
			for (int i = 0; i < categories.size(); i++) {
				Element category = categories.get(i);	
				String categoryName = category.getAttributeValue("name");
				String iconId = category.getChildText("iconId");
				String description = category.getChildText("description");
				List<String> healthyChoices = new ArrayList<String>();				
				loadNutritionItemList(category, "healthyChoices", healthyChoices);								
				NutritionCategoryBean bean = new NutritionCategoryBean(categoryName, iconId, description, healthyChoices);
				nutritionCategoryMap.put(categoryName, bean);
			}
						
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		return nutritionCategoryMap;
	}
	
	private static void loadWorkoutsToMap (List<Element> workouts, Map<String, List<WorkoutBean>> workoutMap, boolean isPremium) {
		List<WorkoutBean> workoutList = new ArrayList<WorkoutBean>();
		for (int i = 0; i <workouts.size(); i++) {		
			Element woBean = workouts.get(i);				
			String workoutName = woBean.getAttributeValue("name");				
			List<WorkoutDay> dayList = new ArrayList<WorkoutDay>();
			loadDays(woBean.getChildren("day"), dayList);
			
			WorkoutBean workoutBean = new WorkoutBean(dayList);
			
			if(isPremium) {
				String purchaseId = woBean.getAttributeValue("purchaseId");
				workoutBean.setPurchaseId(purchaseId);
				String purchaseDescription = woBean.getChildren("purchaseDescription").get(0).getText();
				workoutBean.setPurchaseDescription(purchaseDescription);
			}
			
			workoutList.add(workoutBean);
			workoutMap.put(workoutName, workoutList);
		}
	}
	
	private static void loadDays (List<Element> days, List<WorkoutDay> dayList ) {
		for (int i = 0; i < days.size(); i++) {
			Element day = days.get(i);
			String dayName = day.getAttributeValue("name");
			String title = day.getChildText("title");
			String description = day.getChildText("description");
			String completeDescription = day.getChildText("completeDescription");
			
			WorkoutDay woDay = new WorkoutDay(dayName, title, description, completeDescription);
			List<ExerciseBean> exerciseList = new ArrayList<ExerciseBean>();
			loadExercisesOfDay(((Element)day.getChildren("exercises").get(0)).getChildren("exercise"), exerciseList);
			woDay.setExerciseList(exerciseList);
			dayList.add(woDay);
		}
	}
	
	private static void loadExercisesOfDay (List<Element> exercises, List<ExerciseBean> exerciseList) {
		for (int i = 0; i < exercises.size(); i++) {
			Element exercise = exercises.get(i);
			String exerciseName = exercise.getAttributeValue("name");
			String block = exercise.getChildText("block");
			String setOrRep = exercise.getChildText("setOrRep");
			String note = exercise.getChildText("note");
			String time = exercise.getChildText("time");
			ExerciseBean exerciseBean = new ExerciseBean(exerciseName, block, setOrRep, note, time);
			exerciseList.add(exerciseBean);
		}
	}
	
	private static void loadNutritionItemList (Element category, String listName, List<String> resultList) {				
		List<Element> list = ((Element)category.getChildren(listName).get(0)).getChildren("item");
		for (int i = 0; i < list.size(); i++) {
			Element element = list.get(i);			
			String item = element.getText();
			resultList.add(item);			
		}
	}
	
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		// Get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
}
