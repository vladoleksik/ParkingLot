<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Add Car">
    <h1>Add Car</h1>
    <form class="row g-3 needs-validation" method="POST" action="${pageContext.request.contextPath}/AddCar" novalidate>
        <div class="col-md-6">
            <label for="licensePlate" class="form-label">License plate</label>
            <input type="text" class="form-control" id="licensePlate" name="license_plate" required>
            <div class="invalid-feedback">Please provide a license plate.</div>
        </div>

        <div class="col-md-6">
            <label for="parkingSpot" class="form-label">Parking spot</label>
            <input type="text" class="form-control" id="parkingSpot" name="parking_spot" required>
            <div class="invalid-feedback">Please provide a parking spot.</div>
        </div>

        <div class="col-12">
            <label for="ownerId" class="form-label">Owner</label>
            <select class="form-select" id="ownerId" name="owner_id" required>
                <option value="">Choose...</option>
                <c:forEach var="owner" items="${users}">
                    <option value="${owner.id}">${owner.username}</option>
                </c:forEach>
            </select>
            <div class="invalid-feedback">Please select an owner.</div>
        </div>

        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Save</button>
        </div>
        </c:if>
    </form>
</t:pageTemplate>