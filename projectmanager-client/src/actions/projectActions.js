import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

export const createProject = (project, history) => async dispatch => {
  try {
    //do post if it works redirect to dashboard and dispatch
    const res = await axios.post("/api/project", project);
    history.push("/dashboard");
    //wipe state of errors if updated
    dispatch({ type: GET_ERRORS, payload: {} });
  } catch (err) {
    dispatch({ type: GET_ERRORS, payload: err.response.data });
  }
};

export const getProjects = () => async dispatch => {
  const res = await axios.get("/api/project/all");
  dispatch({ type: GET_PROJECTS, payload: res.data });
};

export const getProject = (id, history) => async dispatch => {
  try {
    const res = await axios.get(`/api/project/${id}`);
    dispatch({ type: GET_PROJECT, payload: res.data });
  } catch (error) {
    history.push("/dashboard");
  }
};

export const deleteProject = id => async dispatch => {
  if (
    window.confirm(
      "Warning, This will delete all data associate with this project"
    )
  ) {
    //delete in server
    await axios.delete(`/api/project/${id}`);
    //return dispatch to reduce and correct state
    dispatch({
      type: DELETE_PROJECT,
      payload: id
    });
  }
};
