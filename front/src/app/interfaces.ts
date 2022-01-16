export interface User {
    id: Number;
    username: String;
    email: String;
    password: String;
    roles: [];
    nom: String;
    prenom: String;
    manager: any;
    latest_cr : any;
  }

  export  interface Roles {
    id: number;
    name: string;
  }

  export interface Project {
    id_project: Number;
    project_name: String;
    manager: User;
  }

  export interface Time {
    id_time: Number,
    date_saisie: Date,
    date_travail: Date,
    nb_hours: String,
    user: User,
    project: Project,
  }
  