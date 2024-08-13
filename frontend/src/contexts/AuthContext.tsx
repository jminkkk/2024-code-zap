import { createContext, useState, ReactNode, useEffect } from 'react';

import { MemberInfo } from '@/types/authentication';

interface AuthContextProps {
  isLogin: boolean;
  memberInfo: MemberInfo;
  handleLoginState: (state: boolean) => void;
  handleMemberInfo: (newMemberInfo: MemberInfo) => void;
}

export const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isLogin, setIsLogin] = useState(false);
  const [memberInfo, setMemberInfo] = useState<MemberInfo>({
    memberId: undefined,
    username: undefined,
  });

  useEffect(() => {
    const savedUsername = localStorage.getItem('username');
    const savedMemberId = localStorage.getItem('memberId');

    if (savedUsername && savedMemberId) {
      setIsLogin(true);
      setMemberInfo({
        username: savedUsername,
        memberId: Number(savedMemberId),
      });
    }
  }, []);

  const handleLoginState = (state: boolean) => {
    setIsLogin(state);
  };

  const handleMemberInfo = (newMemberInfo: MemberInfo) => {
    setMemberInfo(newMemberInfo);
  };

  return (
    <AuthContext.Provider value={{ isLogin, memberInfo, handleLoginState, handleMemberInfo }}>
      {children}
    </AuthContext.Provider>
  );
};
