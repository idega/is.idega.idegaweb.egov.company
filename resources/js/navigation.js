function setGroups(data) {
	DWRUtil.removeAllOptions("prm_group_pk");
	DWRUtil.removeAllOptions("prm_sub_group_pk");

	DWRUtil.addOptions("prm_group_pk", data);
}

function changeGroups(groupType, country, change) {
	if (change) {
		DWRUtil.removeAllOptions("prm_course_pk");
	}
	FSKDWRUtil.getAllGroups(DWRUtil.getValue("prm_division_pk"), groupType, country, setGroups);
	if (change) {
		changeCourses(DWRUtil.getValue("prm_company_pk"), DWRUtil.getValue("prm_season_pk"), DWRUtil.getValue("prm_period_pk"), DWRUtil.getValue("prm_division_pk"), "-1", "-1", country);
	}
}

function setSubGroups(data) {
	DWRUtil.removeAllOptions("prm_sub_group_pk");
	DWRUtil.addOptions("prm_sub_group_pk", data);
}

function changeSubGroups(groupType, country, change) {
	if (change) {
		DWRUtil.removeAllOptions("prm_course_pk");
	}
	FSKDWRUtil.getAllGroups(DWRUtil.getValue("prm_group_pk"), groupType, country, setSubGroups);
	if (change) {
		changeCourses(DWRUtil.getValue("prm_company_pk"), DWRUtil.getValue("prm_season_pk"), DWRUtil.getValue("prm_period_pk"), DWRUtil.getValue("prm_division_pk"), DWRUtil.getValue("prm_group_pk"), "-1", country);
	}
}

function setCourses(data) {
	DWRUtil.removeAllOptions("prm_course_pk");
	DWRUtil.addOptions("prm_course_pk", data);
}

function changeAllCourses(country) {
	changeCourses(DWRUtil.getValue("prm_company_pk"), DWRUtil.getValue("prm_season_pk"), DWRUtil.getValue("prm_period_pk"), DWRUtil.getValue("prm_division_pk"), DWRUtil.getValue("prm_group_pk"), DWRUtil.getValue("prm_sub_group_pk"), country);
}

function changeCourses(companyPK, seasonPK, periodPK, divisionPK, groupPK, subGroupPK, country) {
	FSKDWRUtil.getAllCourses(companyPK, seasonPK, periodPK, divisionPK, groupPK, subGroupPK, country, setCourses);
}

function setPeriods(data) {
	DWRUtil.removeAllOptions("prm_period_pk");
	DWRUtil.addOptions("prm_period_pk", data);
}

function changePeriods() {
	FSKDWRUtil.getPeriods(DWRUtil.getValue("prm_season_pk"), "IS", setPeriods);
}