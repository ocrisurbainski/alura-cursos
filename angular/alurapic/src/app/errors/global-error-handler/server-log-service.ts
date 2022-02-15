import { HttpClient } from "@angular/common/http";
import {Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { ServerLog } from "./server-log";

@Injectable({ providedIn: 'root'})
export class ServerLogService {

    constructor(private http: HttpClient) {}

    log(serverLog: ServerLog) {
        return this.http.post(`${environment.apiServerLog}infra/log`, serverLog);
    }

}