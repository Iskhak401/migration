package io.caden.transformers.ahk.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.caden.transformers.ahk.entities.AHKActivitySummary;
import io.caden.transformers.ahk.entities.AHKStep;
import io.caden.transformers.ahk.entities.AHKWorkout;
import io.caden.transformers.ahk.repositories.AHKActivitySummaryRepository;
import io.caden.transformers.ahk.repositories.AHKWorkoutRepository;
import io.caden.transformers.ahk.utils.AppleHealthKitConstants;
import io.caden.transformers.shared.utils.DateUtil;
import io.caden.transformers.shared.utils.RDFUtil;
import org.springframework.stereotype.Service;

@Service
public class AHKTransformationHelper {
    public void transformWorkouts(final UUID cadenAlias, final JSONArray jsonArray, AHKWorkoutRepository ahkWorkoutRepo) throws JSONException, Exception {
        if (jsonArray == null) {
          return;
        }
    
        for (int counter = 0; counter < jsonArray.length(); counter++) {
          JSONObject jsonWorkout = jsonArray.getJSONObject(counter);
          AHKWorkout workout = new AHKWorkout();
          workout.setCadenAlias(cadenAlias);
          workout.setExerciseType(jsonWorkout.optString(AppleHealthKitConstants.WORKOUT_ACTIVITY_TYPE));
          workout.setDistance(jsonWorkout.optDouble(AppleHealthKitConstants.TOTAL_DISTANCE));
          workout.setTotalFlightsClimbed(jsonWorkout.optDouble(AppleHealthKitConstants.TOTAL_FLIGHTS_CLIMBED));
          workout.setTotalEnergyBurned(jsonWorkout.optDouble(AppleHealthKitConstants.TOTAL_ENERGY_BURNED));
          workout.setTotalSwimmingStrokeCount(jsonWorkout.optDouble(AppleHealthKitConstants.TOTAL_SWIMMING_STROKE_COUNT));
          workout.setDuration(jsonWorkout.optLong(AppleHealthKitConstants.DURATION));
          workout.setStartDate(RDFUtil.getDate(jsonWorkout.optString(AppleHealthKitConstants.START_DATE)));
          workout.setEndDate(RDFUtil.getDate(jsonWorkout.optString(AppleHealthKitConstants.END_DATE)));
    
          ahkWorkoutRepo.update(workout);
        }
      }
    
      public void transformActivities(final UUID cadenAlias, final JSONArray jsonArray, final List<AHKStep> steps, AHKActivitySummaryRepository ahkActivitySummaryRepo) throws JSONException, Exception {
        if (jsonArray == null) {
          return;
        }
    
        List<AHKStep> nonNullStartDateSteps = steps.stream().filter(x -> x.getStartDate() != null).collect(Collectors.toList());
    
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonAS = jsonArray.getJSONObject(i);
          AHKActivitySummary activitySummary = new AHKActivitySummary();
          activitySummary.setCadenAlias(cadenAlias);
          activitySummary.setActiveEnergyBurned(jsonAS.optDouble(AppleHealthKitConstants.ACTIVE_ENERGY_BURNED));
          activitySummary.setActiveEnergyBurnedGoal(jsonAS.optDouble(AppleHealthKitConstants.ACTIVE_ENERGY_BURNED_GOAL));
          activitySummary.setMoveTime(jsonAS.optLong(AppleHealthKitConstants.MOVE_TIME));
          activitySummary.setMoveTimeGoal(jsonAS.optLong(AppleHealthKitConstants.MOVE_TIME_GOAL));
          activitySummary.setExerciseTime(jsonAS.optLong(AppleHealthKitConstants.EXERCISE_TIME));
          activitySummary.setExerciseTimeGoal(jsonAS.optLong(AppleHealthKitConstants.EXERCISE_TIME_GOAL));
          activitySummary.setStandHours(jsonAS.optDouble(AppleHealthKitConstants.STAND_HOURS));
          activitySummary.setStandHoursGoal(jsonAS.optDouble(AppleHealthKitConstants.STAND_HOURS_GOAL));
          activitySummary.setStartDate(RDFUtil.getDate(jsonAS.optString(AppleHealthKitConstants.START_DATE)));
          activitySummary.setEndDate(RDFUtil.getDate(jsonAS.optString(AppleHealthKitConstants.END_DATE)));
    
          if (activitySummary.getStartDate() != null && activitySummary.getEndDate() != null) {
            activitySummary.setStepCount(
              nonNullStartDateSteps.stream().filter(x ->
                DateUtil.compareToInclusive(
                  DateUtil.getDateWithoutTime(x.getStartDate()),
                  DateUtil.getDateWithoutTime(activitySummary.getStartDate()),
                  DateUtil.getDateWithoutTime(activitySummary.getEndDate())
                )
              ).mapToInt(AHKStep::getCount).sum()
            );
          }
    
          ahkActivitySummaryRepo.update(activitySummary);
        }
      }
    
      public List<AHKStep> getSteps(final JSONArray jsonArray) throws JSONException, Exception {
        List<AHKStep> steps = new ArrayList<>();
    
        if (jsonArray != null) {
          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonStep = jsonArray.getJSONObject(i);
      
            AHKStep step = new AHKStep();
            step.setEndDate(RDFUtil.getDate(jsonStep.optString(AppleHealthKitConstants.ENDDATE)));
            step.setStartDate(RDFUtil.getDate(jsonStep.optString(AppleHealthKitConstants.STARTDATE)));
            step.setCount(jsonStep.optInt(AppleHealthKitConstants.STEPCOUNT));
    
            steps.add(step);
          }
        }
    
        return steps;
      }
}
