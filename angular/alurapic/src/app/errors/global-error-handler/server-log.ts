export interface ServerLog { 
    message: string;
    url: string;
    stack: string;
    user: ServerUsuarioLog;
}

export interface ServerUsuarioLog {
    userId: number;
    userName: string;
}