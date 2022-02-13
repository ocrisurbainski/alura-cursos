import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PhotoListComponent } from './photos/photo-list/photo-list.component';
import { PhotoFormComponent } from './photos/photo-form/photo-form.component';
import { NotFoundComponent } from './errors/not-found/not-found.component';
import { PhotoListResolver } from './photos/photo-list/photo-list.resolver';
import { AuthGuardService } from './core/auth/auth-guard.service';
import { PhotoDetailsComponent } from './photos/photo-details/photo-details.component';

const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'home'
    },
    {
        path: 'home',
        loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
    },
    {
        path: 'user/:username', 
        component: PhotoListComponent, 
        resolve: {
            photos: PhotoListResolver
        }
    },
    {
        path: 'p/add', 
        component: PhotoFormComponent,
        canActivate: [AuthGuardService]
    },
    {
        path: 'p/:photoId', 
        component: PhotoDetailsComponent,
    },
    {
        path: 'not-found',
        component: NotFoundComponent
    },
    {
        path: '**', 
        redirectTo: 'not-found'
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
