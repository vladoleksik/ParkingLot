<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Add User">
    <h1>Add User</h1>
    <form class="row g-3 needs-validation" method="POST" action="${pageContext.request.contextPath}/AddUser" novalidate>
        <div class="col-md-6">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
            <div class="invalid-feedback">Please provide a username.</div>
        </div>

        <div class="col-md-6">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" id="email" name="email" required>
            <div class="invalid-feedback">Please provide an email.</div>
        </div>

        <div class="col-md-6">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
            <div class="invalid-feedback">Please set a password.</div>
        </div>

        <div class="col-12">
            <label for="user_groups" class="form-label">Groups</label>
            <select class="custom-select d-block w-100" id="user_groups" name="user_groups" multiple>
                <option value="">Choose...</option>
                <c:forEach var="user_group" items="${userGroups}" varStatus="status">
                    <option value="${user_group}">${user_group}</option>
                </c:forEach>
            </select>
        </div>

        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Save</button>
        </div>
        </c:if>
    </form>
</t:pageTemplate>