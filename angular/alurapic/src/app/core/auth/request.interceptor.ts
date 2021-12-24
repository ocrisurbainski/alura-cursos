import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    constructor(private tokenService: TokenService) {}

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

        debugger
        if (this.tokenService.hasToken()) {

            const token = this.tokenService.getToken()!;

            request = request.clone({
                setHeaders: {
                    'x-access-token': token,
                },
            });
        }

        return next.handle(request);
    }
}
