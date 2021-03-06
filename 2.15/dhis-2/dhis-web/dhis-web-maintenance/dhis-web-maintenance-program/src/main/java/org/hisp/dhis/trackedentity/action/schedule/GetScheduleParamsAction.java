package org.hisp.dhis.trackedentity.action.schedule;

/*
 * Copyright (c) 2004-2014, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import static org.hisp.dhis.setting.SystemSettingManager.DEFAULT_TIME_FOR_SENDING_MESSAGE;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_TIME_FOR_SENDING_MESSAGE;

import org.hisp.dhis.setting.SystemSettingManager;
import org.hisp.dhis.system.scheduling.Scheduler;
import org.hisp.dhis.trackedentity.scheduling.ProgramSchedulingManager;

import com.opensymphony.xwork2.Action;

/**
 * @author Chau Thu Tran
 * 
 * @version GetScheduleParamsAction.java 10:57:08 AM Aug 9, 2012 $
 */
public class GetScheduleParamsAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private SystemSettingManager systemSettingManager;

    public void setSystemSettingManager( SystemSettingManager systemSettingManager )
    {
        this.systemSettingManager = systemSettingManager;
    }
    
    private ProgramSchedulingManager schedulingManager;

    public void setSchedulingManager( ProgramSchedulingManager schedulingManager )
    {
        this.schedulingManager = schedulingManager;
    }

    // -------------------------------------------------------------------------
    // Input/Output
    // -------------------------------------------------------------------------

    private String timeSendingMessage;

    public String getTimeSendingMessage()
    {
        return timeSendingMessage;
    }

    private String status;

    public String getStatus()
    {
        return status;
    }

    private boolean running;

    public boolean isRunning()
    {
        return running;
    }

    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

    @Override
    public String execute()
        throws Exception
    {
        timeSendingMessage = (String)systemSettingManager.getSystemSetting( KEY_TIME_FOR_SENDING_MESSAGE, DEFAULT_TIME_FOR_SENDING_MESSAGE );
        
        status = schedulingManager.getTaskStatus();
        running = Scheduler.STATUS_RUNNING.equals( status );

        return SUCCESS;
    }

}
