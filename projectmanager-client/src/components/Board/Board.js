import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import Backlog from "./Backlog";
import PropTypes from "prop-types";
import { getBacklog } from "../../actions/backlogActions";

class Board extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }
  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    const { errors } = this.state;

    let content;

    const boardAlgorithm = (errors, project_tasks) => {
      if (project_tasks.length < 1) {
        if (errors.propjectNotFound) {
          return (
            <div className="alert alert-danger" role="alert">
              {errors.propjectNotFound}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks were found
            </div>
          );
        }
      } else {
        return <Backlog project_tasks={project_tasks} />;
      }
    };
    content = boardAlgorithm(errors, project_tasks);
    return (
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        {content}
      </div>
    );
  }
}

Board.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  backlog: state.backlog,
  errors: state.errors
});

export default connect(mapStateToProps, { getBacklog })(Board);
