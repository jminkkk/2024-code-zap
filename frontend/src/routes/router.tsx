import { createBrowserRouter } from 'react-router-dom';

import { Layout } from '@/components';
import {
  TemplatePage,
  MyTemplatePage,
  TemplateUploadPage,
  SignupPage,
  LoginPage,
  LandingPage,
  NotFoundPage,
} from '@/pages';
import AuthGuard from './AuthGuard';
import GuestGuard from './GuestGuard';

const router = createBrowserRouter([
  {
    errorElement: <NotFoundPage />,
    element: <Layout />,
    children: [
      {
        path: '/',
        element: (
          <GuestGuard>
            <MyTemplatePage />
          </GuestGuard>
        ),
      },
      {
        path: 'templates/:id',
        element: (
          <GuestGuard>
            <TemplatePage />
          </GuestGuard>
        ),
      },
      {
        path: 'templates/upload',
        element: (
          <GuestGuard>
            <TemplateUploadPage />
          </GuestGuard>
        ),
      },
      {
        path: 'signup',
        element: (
          <AuthGuard>
            <SignupPage />
          </AuthGuard>
        ),
      },
      {
        path: 'login',
        element: (
          <AuthGuard>
            <LoginPage />
          </AuthGuard>
        ),
      },
      {
        path: 'home',
        element: (
          <AuthGuard>
            <LandingPage />,
          </AuthGuard>
        ),
      },
      {
        path: '*',
        element: <NotFoundPage />,
      },
    ],
  },
]);

export default router;
