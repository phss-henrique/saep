import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { authGuard } from './auth-guard';
import { Produtos } from './pages/produtos/produtos';
import { Estoque } from './pages/estoque/estoque';

export const routes: Routes = [
    {path:'', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component:Login},
    
    {
        path: 'produtos',
        component: Produtos,
        canActivate: [authGuard]
    },
    {
        path: 'estoque',
        component: Estoque,
        canActivate: [authGuard]
    }
];
