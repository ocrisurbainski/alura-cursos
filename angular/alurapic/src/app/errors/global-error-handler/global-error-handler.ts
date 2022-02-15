import { LocationStrategy, PathLocationStrategy } from "@angular/common";
import { ErrorHandler, Injectable, Injector } from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/core/user/user.service";
import { environment } from "src/environments/environment";
import * as stackTrace from "stacktrace-js";
import { ServerLogService } from "./server-log-service";

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

    constructor(private injector: Injector) {}

    handleError(error: any): void {
        const location = this.injector.get(LocationStrategy);
        const userService = this.injector.get(UserService);
        const serverLogService = this.injector.get(ServerLogService);
        const router = this.injector.get(Router);
        
        const url = location instanceof PathLocationStrategy ? location.path() : '';

        const message = error.message ? error.message : error.toString();

        if (environment.production) {
            router.navigate(['/error']);
        }
        
        stackTrace.fromError(error).then(stackFrames => {
            const stackAsString = stackFrames.map(sf => sf.toString()).join("\n");
            
            console.log(message);
            console.log(stackAsString);

            const logData = {
                message,
                url,
                stack: stackAsString,
                user: {
                    userId: userService.getUserId(),
                    userName: userService.getUserName(),
                },
            };

            console.log(logData);

            serverLogService.log(logData).subscribe({
                next: () => console.log('Error logged on server'),
                error: (err) => {
                    console.log(err);
                    console.log('Fail to send error log to server');
                }
            });
        });
    }
}