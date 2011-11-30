/*
* Copyright (c) 2007, Xfresh Project
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the Xfresh Project nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED `AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL Xfresh Project BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.xfresh.core.impl;

import net.sf.xfresh.core.*;
import net.sf.xfresh.ext.AuthHandler;
import net.sf.xfresh.ext.auth.AlwaysNoAuthHandler;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.XMLFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: 21.04.2007
 * Time: 15:40:54
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.net
 */
public class DefaultYaletSupport implements YaletSupport {

    protected SingleYaletProcessor singleYaletProcessor;

    protected AuthHandler authHandler = new AlwaysNoAuthHandler();

    @Required
    public void setSingleYaletProcessor(final SingleYaletProcessor singleYaletProcessor) {
        this.singleYaletProcessor = singleYaletProcessor;
    }

    public void setAuthHandler(final AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    public InternalRequest createRequest(final HttpServletRequest httpServletRequest, final String realPath) {
        final SimpleInternalRequest request = new SimpleInternalRequest(httpServletRequest, realPath);
        request.setUserId(authHandler.getUserId(request));
        return request;
    }

    public InternalResponse createResponse(final HttpServletResponse httpServletResponse) {
        return new SimpleInternalResponse(httpServletResponse);
    }

    public XMLFilter createFilter(final InternalRequest request, final InternalResponse response) {
        return new YaletFilter(singleYaletProcessor, request, response);
    }
}
