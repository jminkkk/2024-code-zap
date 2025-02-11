import { ErrorBoundary } from '@sentry/react';
import { useEffect, useRef } from 'react';
import { Outlet } from 'react-router-dom';

import { Header } from '@/components';
import { useHeaderHeight } from '@/hooks/utils/useHeaderHeight';
import { NotFoundPage } from '@/pages';
import * as S from './Layout.style';

const Layout = () => {
  const headerRef = useRef<HTMLDivElement>(null);
  const { setHeaderHeight } = useHeaderHeight();

  useEffect(() => {
    if (headerRef.current) {
      setHeaderHeight(headerRef.current.offsetHeight);
    }
  }, [setHeaderHeight]);

  return (
    <S.LayoutContainer>
      <Header headerRef={headerRef} />
      <S.Wrapper>
        <ErrorBoundary fallback={() => <NotFoundPage />}>
          <Outlet />
        </ErrorBoundary>
      </S.Wrapper>
    </S.LayoutContainer>
  );
};

export default Layout;
