package com.example.zhengxiaohu.cncmonitor.api.data.machine.controller;

import android.util.Log;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by XiaoHu Zheng on 2017/5/25.
 * Email: 1050087728@qq.com
 */

public class ControllerPathInfo extends ControllerInfo {

    //controller mode of cnc controller
    private String controllerMode;
    //execution mode of cnc controller
    private String execution;
    //status of system
    private String systemStatus;
    //message of system
    private String systemMessage;
    //the position of tool tip in work coordinate system
    private String pathPosition;
    //actual pathFeedRate
    private String pathFeedRateActual;
    //programmed pathFeedRate
    private String pathFeedRateProgrammed;
    //rapid pathFeedRate
    private String pathFeedRateRapid;
    //override of axis feedRate
    private String pathFeedRateOverride;
    //name of program
    private String program;
    //name of block
    private String block;
    //total line of program
    private String lineTotal;
    //the current line of program
    private String line;
    //the count of product
    private String partCount;
    //the time of machining
    private String machiningTime;
    //the tool number
    private String toolNumber;

    private static final String EVENTS ="Events";
    private static final String SAMPLES = "Samples";
    private static final String DATA_ITEM_ID = "dataItemId";
    private static final String NAME="name";
    private static final String COMPONENT_ID="componentId";

    //dataItemId for Samples
    private static final String PATH_POSITION="path_position";
    private static final String PATH_FEEDRATE_ACTUAL="path_feedrate_actual";
    private static final String PATH_FEEDRATE_PROGRAMMED="path_feedrate_programmed";
    private static final String PATH_FEEDRATE_RAPID="path_feedrate_rapid";
    //dataItemId for Events
    private static final String CONTROLLER_MODE="controller_mode";
    private static final String EXECUTION="exec";
    private static final String PATH_FEEDRATE_OVERRIDE="path_feedrate_override";
    private static final String PROGRAM="program";
    private static final String BLOCK="block";
    private static final String LINE_TOTAL="lineTotal";
    private static final String LINE="line";
    private static final String PART_COUNT="part_count";
    private static final String MACHINING_TIME="machining_time";
    private static final String TOOL_NUMBER="tool_number";

    private static final String TAG = "ControllerPathInfo";

    public String getControllerMode() {
        return controllerMode;
    }

    public void setControllerMode(String controllerMode) {
        this.controllerMode = controllerMode;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getPathPosition() {
        return pathPosition;
    }

    public void setPathPosition(String pathPosition) {
        this.pathPosition = pathPosition;
    }

    public String getPathFeedRateActual() {
        return pathFeedRateActual;
    }

    public void setPathFeedRateActual(String pathFeedRateActual) {
        this.pathFeedRateActual = pathFeedRateActual;
    }

    public String getPathFeedRateProgrammed() {
        return pathFeedRateProgrammed;
    }

    public void setPathFeedRateProgrammed(String pathFeedRateProgrammed) {
        this.pathFeedRateProgrammed = pathFeedRateProgrammed;
    }

    public String getPathFeedRateRapid() {
        return pathFeedRateRapid;
    }

    public void setPathFeedRateRapid(String pathFeedRateRapid) {
        this.pathFeedRateRapid = pathFeedRateRapid;
    }

    public String getPathFeedRateOverride() {
        return pathFeedRateOverride;
    }

    public void setPathFeedRateOverride(String pathFeedRateOverride) {
        this.pathFeedRateOverride = pathFeedRateOverride;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPartCount() {
        return partCount;
    }

    public void setPartCount(String partCount) {
        this.partCount = partCount;
    }

    public String getMachiningTime() {
        return machiningTime;
    }

    public void setMachiningTime(String machiningTime) {
        this.machiningTime = machiningTime;
    }

    public String getToolNumber() {
        return toolNumber;
    }

    public void setToolNumber(String toolNumber) {
        this.toolNumber = toolNumber;
    }

    public static ControllerPathInfo parse(Element pathComponentStream){
        ControllerPathInfo result=null;
        if (pathComponentStream==null){
            Log.d(TAG, "parse: pathComponentStream is null");
        }
        if (pathComponentStream!=null){
            result=new ControllerPathInfo();

            Element samples=pathComponentStream.element(SAMPLES);
            Element events=pathComponentStream.element(EVENTS);

            //Samples
            Element pathPositionActual=null;
            Element pathFeedrateActual=null;
            Element pathFeedrateProgrammed=null;
            Element pathFeedrateRapid=null;
            //Events
            Element controllerMode=null;
            Element execution=null;
            Element pathFeedrateOverride=null;
            Element program=null;
            Element block=null;
            Element lineTotal=null;
            Element line=null;
            Element partCount=null;
            Element machiningTime=null;
            Element toolNumber=null;

            //Samples
            if (samples==null){
                Log.d(TAG, "parse: samples is null");
            }
            if (samples!=null){
                for(Iterator iterator=samples.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case PATH_POSITION:
                            pathPositionActual=element;
                            break;
                        case PATH_FEEDRATE_ACTUAL:
                            pathFeedrateActual=element;
                            break;
                        case PATH_FEEDRATE_PROGRAMMED:
                            pathFeedrateProgrammed=element;
                            break;
                        case PATH_FEEDRATE_RAPID:
                            pathFeedrateRapid=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            //events
            if (events==null){
                Log.d(TAG, "parse: events is null");
            }
            if (events!=null){
                for (Iterator iterator=events.elementIterator();iterator.hasNext();){
                    Element element= (Element) iterator.next();
                    switch (element.attributeValue(DATA_ITEM_ID)){
                        case CONTROLLER_MODE:
                            controllerMode=element;
                            break;
                        case EXECUTION:
                            execution=element;
                            break;
                        case PATH_FEEDRATE_OVERRIDE:
                            pathFeedrateOverride=element;
                            break;
                        case PROGRAM:
                            program=element;
                            break;
                        case BLOCK:
                            block=element;
                            break;
                        case LINE_TOTAL:
                            lineTotal=element;
                            break;
                        case LINE:
                            line=element;
                            break;
                        case PART_COUNT:
                            partCount=element;
                            break;
                        case MACHINING_TIME:
                            machiningTime=element;
                            break;
                        case TOOL_NUMBER:
                            toolNumber=element;
                            break;
                        default:
                            break;
                    }
                }
            }

            result.setName(pathComponentStream.attributeValue(NAME));
            result.setId(pathComponentStream.attributeValue(COMPONENT_ID));
            //pathPositionActual
            if (pathPositionActual==null){
                Log.d(TAG, "parse: pathPositionActual is null");
            }
            if (pathPositionActual!=null){
                result.setPathPosition(pathPositionActual.getStringValue());
            }
            //pathFeedrateActual
            if (pathFeedrateActual==null){
                Log.d(TAG, "parse: pathFeedrateActual is null");
            }
            if (pathFeedrateActual!=null){
                result.setPathFeedRateActual(pathFeedrateActual.getStringValue());
            }
            //pathFeedrateProgrammed
            if (pathFeedrateProgrammed==null){
                Log.d(TAG, "parse: pathFeedrateProgrammed is null");
            }
            if (pathFeedrateProgrammed!=null){
                result.setPathFeedRateProgrammed(pathFeedrateProgrammed.getStringValue());
            }
            //pathFeedrateRapid
            if (pathFeedrateRapid==null){
                Log.d(TAG, "parse: pathFeedrateRapid is null");
            }
            if (pathFeedrateRapid!=null){
                result.setPathFeedRateRapid(pathFeedrateRapid.getStringValue());
            }
            //controllerMode
            if (controllerMode==null){
                Log.d(TAG, "parse: controllerMode is null");
            }
            if (controllerMode!=null){
                result.setControllerMode(controllerMode.getStringValue());
            }
            //execution
            if (execution==null){
                Log.d(TAG, "parse: execution is null");
            }
            if (execution!=null){
                result.setExecution(execution.getStringValue());
            }
            //pathFeedrateOverride
            if (pathFeedrateOverride==null){
                Log.d(TAG, "parse: pathFeedrateOverride is null");
            }
            if (pathFeedrateOverride!=null){
                result.setPathFeedRateOverride(pathFeedrateOverride.getStringValue());
            }
            //program
            if (program==null){
                Log.d(TAG, "parse: program is null");
            }
            if (program!=null){
                result.setProgram(program.getStringValue());
            }
            //block
            if (block==null){
                Log.d(TAG, "parse: block is null");
            }
            if (block!=null){
                result.setBlock(block.getStringValue());
            }
            if (lineTotal==null){
                Log.d(TAG, "parse: lineTotal is null");
            }
            if (lineTotal!=null){
                result.setLineTotal(lineTotal.getStringValue());
            }
            //line
            if (line==null){
                Log.d(TAG, "parse: line is null");
            }
            if (line!=null){
                result.setLine(line.getStringValue());
            }
            //part count
            if (partCount==null){
                Log.d(TAG, "parse: partCount is null");
            }
            if (partCount!=null){
                result.setPartCount(partCount.getStringValue());
            }
            //machining time
            if (machiningTime==null){
                Log.d(TAG, "parse: machiningTime is null");
            }
            if (machiningTime!=null){
                result.setMachiningTime(machiningTime.getStringValue());
            }
            //toolNumber
            if (toolNumber==null){
                Log.d(TAG, "parse: toolNumber is null");
            }
            if (toolNumber!=null){
                result.setToolNumber(toolNumber.getStringValue());
            }
        }
        return result;
    }
}
