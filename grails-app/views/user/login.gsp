<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.login.label" /></title>
	</head>
	<body>
		<a href="#login-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="login-user" class="content" role="main">
			<h1><g:message code="default.login.label" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:form url="[resource:userInstance, action:'authenticate']" >
				<fieldset class="form">
          <div class="fieldcontain login required">
            <label for="login">
              <g:message code="user.login.label" default="Login" />
              <span class="required-indicator">*</span>
            </label>
            <g:textField name="login" required="" value="${userInstance?.login}"/>
          </div>

          <div class="fieldcontain password required">
            <label for="password">
              <g:message code="user.password.label" default="Password" />
              <span class="required-indicator">*</span>
            </label>
            <g:field type="password" name="password" required=""/>
          </div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="authenticate" value="${message(code: 'default.button.login.label', default: 'Login')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
