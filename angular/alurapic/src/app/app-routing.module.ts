import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PhotoListComponent } from './photos/photo-list/photo-list.component';
import { PhotoFormComponent } from './photos/photo-form/photo-form.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { PhotoListResolver } from './photos/photo-list/photo-list.resolver';
import { AuthGuardService } from './core/auth/auth-guard.service';
import { PhotoDetailsComponent } from './photos/photo-details/photo-details.component';
import { GlobalErrorComponent } from './errors/global-error/global-error.component';

const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'home',
    },
    {
        path: 'home',
        loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
    },
    {
        path: 'user/:username', 
        component: PhotoListComponent, 
        resolve: {
            photos: PhotoListResolver
        },
        data: {
            title: 'Timeline',
        },
    },
    {
        path: 'p/add', 
        component: PhotoFormComponent,
        canActivate: [AuthGuardService],
        data: {
            title: 'Photo Upload',
        },
    },
    {
        path: 'p/:photoId', 
        component: PhotoDetailsComponent,
        data: {
            title: 'Photo details',
        },
    },
    {
        path: 'not-found',
        component: NotFoundComponent,
        data: {
            title: 'Not found',
        },
    },
    {
        path: 'error',
        component: GlobalErrorComponent,
        data: { 
            title: 'Error'
        }
    },
    {
        path: '**', 
        redirectTo: 'not-found',
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes),
    ],
    exports: [
        RouterModule,
    ]
})
export class AppRoutingModule { }
